package com.Alif.ChatAppX.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String type;
    private String createAt;
    private String updatedAt;
    @OneToOne(mappedBy = "chats")
    private User senderUser;
    @OneToOne(mappedBy = "chats")
    private User recipientUser;
    private String sender;
    private String recipient;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;

}
