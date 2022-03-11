package com.example.janghj.chat.domain;

import com.example.janghj.chat.dto.ChatRoomDTO;
import com.example.janghj.domain.Timestamped;
import com.example.janghj.domain.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;

    @Column
    private String name;

    @Column
    private int count;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = LAZY)
    private User user; //룸 개설자

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ChatUser> chatUsers;

    public ChatRoom(ChatRoomDTO chatRoomDTO, User user) {
        this.name = chatRoomDTO.getRoomName();
        this.count = 0;
        this.user = user;
    }

}
