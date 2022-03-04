package com.example.janghj.chat.repository;

import com.example.janghj.chat.domain.ChatRoom;
import com.example.janghj.chat.domain.ChatUser;
import com.example.janghj.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    Optional<ChatUser> deleteByChatRoomAndUser(ChatRoom chatRoom, User user);
    Optional<ChatUser> findByChatRoomAndUser(ChatRoom chatRoom, User user);

    int countByChatRoom(ChatRoom chatRoom);
}
