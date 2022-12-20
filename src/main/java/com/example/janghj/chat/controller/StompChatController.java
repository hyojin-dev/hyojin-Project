package com.example.janghj.chat.controller;

import com.example.janghj.chat.domain.ChatRoom;
import com.example.janghj.chat.domain.ChatUser;
import com.example.janghj.chat.dto.ChatMessageDTO;
import com.example.janghj.chat.repository.ChatRoomRepository;
import com.example.janghj.chat.repository.ChatUserRepository;
import com.example.janghj.domain.User.User;
import com.example.janghj.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;
    private final UserRepository userRepository;

    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message) {
        String id = message.getRoomId();
        Long room_id = Long.valueOf(id);

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(room_id).orElseThrow(
                () -> new NullPointerException("해당 채팅방이 존재하지 않습니다. id = " + room_id));
        User user = userRepository.findByUsername(message.getUserName()).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다. id  = " + message.getUserId()));

        //채팅방 처음입장
        if (!(chatUserRepository.findByChatRoomAndUser(chatRoom, user).isPresent())) {
            ChatUser chatUser = new ChatUser(user, chatRoom);
            chatUserRepository.save(chatUser);
            int count = chatUserRepository.countByChatRoom(chatRoom);

            chatRoom.setCount(count);
            chatRoomRepository.save(chatRoom);

            message.setMessage(user.getUsername() + " 님이 채팅방에 참여하였습니다.");
            template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        } else {
            message.setMessage(user.getUsername() + " 님이 채팅방에 재입장하였습니다.");
            template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        }
    }

    @Transactional
    @MessageMapping(value = "/chat/exit")
    public void exit(ChatMessageDTO message) {
        String id = message.getRoomId();
        Long room_id = Long.valueOf(id);

        // chat user 정보 삭제 (채팅유저 , 채팅방)
        ChatRoom chatRoom = chatRoomRepository.findById(room_id).orElseThrow(
                () -> new NullPointerException("해당 채팅방이 존재하지 않습니다. id  = " + room_id));
        User user = userRepository.findByUsername(message.getUserName()).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다. id  = " + message.getUserId()));


        chatUserRepository.deleteByChatRoomAndUser(chatRoom, user);
        int count = chatUserRepository.countByChatRoom(chatRoom);
        chatRoom.setCount(count);
        chatRoomRepository.save(chatRoom);

        message.setMessage(message.getUserName() + " 님이 채팅방을 나갔습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message) {
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}