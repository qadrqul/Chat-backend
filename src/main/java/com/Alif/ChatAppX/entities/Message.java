package com.Alif.ChatAppX.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne()
    private User sender;

    @ManyToOne()
    private User recipient;
    private String content;
    private String messageType;
    private String createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FileData> fileData;

    private boolean isREad;

    @ManyToOne()
    private Chat chat;

    @ManyToOne()
    private Group group;
}
