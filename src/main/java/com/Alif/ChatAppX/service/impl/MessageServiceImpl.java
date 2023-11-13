package com.Alif.ChatAppX.service.impl;

import com.Alif.ChatAppX.dto.message.MessageRequest;
import com.Alif.ChatAppX.entities.Message;
import com.Alif.ChatAppX.repository.UserRepository;
import com.Alif.ChatAppX.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;
    @Override
    public Message toEntity(String payload)   {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            MessageRequest message = objectMapper.readValue(payload, MessageRequest.class);
            Message message1 = new Message();
            message1.setSender(userRepository.findByEmail(message.getSender()).get());
            message1.setRecipient(userRepository.findByEmail(message.getRecipient()).get());
            message1.setContent(message.getContent());
            message1.setMessageType(message.getMessageType());
            message1.setCreatedAt(message.getCreatedAt());
            message1.setREad(message.getIsRead());
            return message1;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Message toEntity(MessageRequest message){
        Message message1 = new Message();
        message1.setSender(userRepository.findByEmail(message.getSender()).get());
        message1.setRecipient(userRepository.findByEmail(message.getRecipient()).get());
        message1.setContent(message.getContent());
        message1.setMessageType(message.getMessageType());
        message1.setCreatedAt(message.getCreatedAt());
        message1.setREad(message.getIsRead());
        return message1;
    }
}
