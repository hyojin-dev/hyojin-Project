package com.example.janghj.chat.domain;

import com.example.janghj.domain.Timestamped;
import com.example.janghj.domain.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class ChatUser extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne(fetch = LAZY)
    private User user;

    @JoinColumn(name = "ROOM_ID", nullable = false)
    @ManyToOne(fetch = LAZY)
    private ChatRoom chatRoom;

    public ChatUser(User user, ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        this.user = user;
    }

}