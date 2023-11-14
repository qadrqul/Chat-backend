package com.Alif.ChatAppX.entities;


import lombok.*;
import org.jboss.logging.Messages;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ChatGroup")
@Table(name = "chat_group")


public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String groupName;

    private String adminNickName;

    @ManyToMany()
    private List<User> users;

    @OneToMany
    private List<Message> messages;

    private String createdTime;
    private String updatedTime;

    @OneToOne(cascade = CascadeType.ALL)
    private FileData groupImage;
}
