package com.example.janghj.chat.service;

import com.example.janghj.chat.domain.ChatRoom;
import com.example.janghj.chat.dto.ChatRoomDTO;
import com.example.janghj.chat.repository.ChatRoomRepository;
import com.example.janghj.domain.User.User;
import com.example.janghj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public List<ChatRoom> getAllRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom createRoom(ChatRoomDTO chatRoomDTO, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. userId = " + id));
        ChatRoom room = new ChatRoom(chatRoomDTO, user);
        chatRoomRepository.save(room);
        return room;
    }

    public ChatRoom getRoom(Long id) {
        return chatRoomRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 채팅방이 존재하지 않습니다. ChatRoodId = " + id)
        );
    }

    public boolean checkmyroom(Long room_id, Long user_id) {
        ChatRoom chatRoom = chatRoomRepository.findById(room_id).orElseThrow(
                () -> new NullPointerException("해당 채팅방이 존재하지 않습니다. ChatRoodId = " + room_id)
        );
        if (chatRoom.getUser().getId().equals(user_id)) {
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteRoom(Long id) {
        chatRoomRepository.deleteById(id);
    }
}
