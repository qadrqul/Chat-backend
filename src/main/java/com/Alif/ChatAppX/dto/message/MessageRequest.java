package com.Alif.ChatAppX.dto.message;

import com.Alif.ChatAppX.dto.file.FileDataResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageRequest {
    private String sender;
    private String recipient;
    private String content;
    private String messageType;
    private List<Long> fileDataIds;
    private String createdAt;
    private Boolean isRead;

}
