package com.Alif.ChatAppX.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeMessage {
    private String type;
    private Long id;
    private MessageRequest messageRequest;
}
