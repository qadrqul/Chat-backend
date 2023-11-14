package com.Alif.ChatAppX.service.impl;

import com.Alif.ChatAppX.dto.message.GroupRequest;
import com.Alif.ChatAppX.dto.message.MessageRequest;
import com.Alif.ChatAppX.entities.Group;
import com.Alif.ChatAppX.entities.Message;
import com.Alif.ChatAppX.entities.User;
import com.Alif.ChatAppX.mapper.FileDataMapper;
import com.Alif.ChatAppX.repository.FileRepository;
import com.Alif.ChatAppX.repository.GroupRepository;
import com.Alif.ChatAppX.repository.UserRepository;
import com.Alif.ChatAppX.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final FileDataMapper fileDataMapper;
    private final GroupRepository groupRepository;
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

    @Override
    public void createGroup(GroupRequest groupRequest) {

        Group group = new Group();
        group.setAdminNickName(groupRequest.getAdminNickname());
        group.setGroupName(groupRequest.getGroupName());
        group.setCreatedTime(LocalDateTime.now().toString());
        group.setMessages(new ArrayList<>());


        List<User> users = new ArrayList<>();
        for (String nickname: groupRequest.getUserNickNames()){
            users.add(userRepository.findByNickname(nickname).get());
        }
        group.setUsers(users);
        if (groupRequest.getGroupImageId()!=null){
            group.setGroupImage(fileRepository.findById(groupRequest.getGroupImageId()).get());

        }
        groupRepository.save(group);


    }
}
