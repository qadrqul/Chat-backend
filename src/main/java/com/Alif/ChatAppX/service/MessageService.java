package com.Alif.ChatAppX.service;

import com.Alif.ChatAppX.dto.message.GroupRequest;
import com.Alif.ChatAppX.dto.message.MessageRequest;
import com.Alif.ChatAppX.entities.Message;

public interface MessageService {
    Message toEntity(String payload);

    Message toEntity(MessageRequest message);

    void createGroup(GroupRequest groupRequest);
}
