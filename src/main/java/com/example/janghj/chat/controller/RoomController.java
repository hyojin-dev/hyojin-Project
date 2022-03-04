package com.example.janghj.chat.controller;

import com.example.janghj.chat.domain.ChatRoom;
import com.example.janghj.chat.dto.ChatRoomDTO;
import com.example.janghj.chat.repository.ChatUserRepository;
import com.example.janghj.chat.service.ChatRoomService;
import com.example.janghj.config.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class RoomController {

    private final ChatRoomService chatRoomService;
    private final ChatUserRepository chatUserRepository;

    @Operation(description = "채팅방 목록 조회", method = "GET")
    @GetMapping(value = "/rooms")
    public List<ChatRoom> rooms() {
        return chatRoomService.getAllRooms();
    }

    @Operation(description = "채팅방 개설", method = "POST")
    @PostMapping(value = "/room")
    public ChatRoom create(@RequestBody ChatRoomDTO chatRoomDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.createRoom(chatRoomDTO, userDetails.getUser().getId());
    }

    @Operation(description = "채팅방 삭제", method = "DELETE")
    @DeleteMapping("/room/{id}")
    public void deleteRoom(@PathVariable Long id) {
        chatRoomService.deleteRoom(id);
    }

    @Operation(description = "채팅방 조회", method = "GET")
    @GetMapping("/room/{id}")
    public ChatRoom getRoom(@PathVariable final Long id) {
        return chatRoomService.getRoom(id);
    }

    @Operation(description = "채팅방 전체 이용자 수 조회", method = "GET")
    @GetMapping("/rooms/users")
    public int getChatUsers() {
        Long users = chatUserRepository.count();
        int users_count = users.intValue();
        return users_count;
    }

    @Operation(description = "내가 만든 채팅방인지 확인", method = "GET")
    @GetMapping("/room/my/{id}")
    public boolean checkMyRoom(@PathVariable final Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.checkmyroom(id, userDetails.getUser().getId());
    }

}