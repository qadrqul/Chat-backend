package com.Alif.ChatAppX.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageResponse {
    private String sender;
    private String recipient;
    private String content;
    private String messageType;
    private List<Long> fileDataResponses;
    private String createdAt;
    private Boolean isRead;
}
