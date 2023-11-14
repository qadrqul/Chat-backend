package com.Alif.ChatAppX.messageConfig;



import com.Alif.ChatAppX.dto.message.MessageRequest;
import com.Alif.ChatAppX.dto.message.TypeMessage;
import com.Alif.ChatAppX.entities.Chat;
import com.Alif.ChatAppX.entities.Message;
import com.Alif.ChatAppX.entities.User;
import com.Alif.ChatAppX.repository.ChatRepository;
import com.Alif.ChatAppX.repository.UserRepository;
import com.Alif.ChatAppX.service.MessageService;
import com.Alif.ChatAppX.service.UserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyHandler implements WebSocketHandler{
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        User sender = userService.getUserFromToken(session.getAttributes().get("token").toString());
        System.out.println(sender.getEmail() + " " + sender.getNickname());
        userSessions.put(sender.getEmail(), session);

        System.out.println("afterConnectionEstablished");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
        String payload = (String) webSocketMessage.getPayload();
        System.out.println("Payload: " + payload);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeMessage typeMessage = objectMapper.readValue(payload, TypeMessage.class);
        MessageRequest messageRequest =  typeMessage.getMessageRequest();

        if (typeMessage.getType().equals("OneToOne")) {

            Optional<Chat> chat = chatRepository.findBySenderAndRecipientOrRecipientAndSender(
                    messageRequest.getSender(),
                    messageRequest.getRecipient(),
                    messageRequest.getSender(),
                    messageRequest.getRecipient()
            );
            if (chat.isEmpty()) {
                Chat newChat = new Chat();
                Message message = messageService.toEntity(messageRequest);
                List<Message> messages = new ArrayList<>();
                messages.add(message);
                newChat.setMessages(messages);
                newChat.setSender(message.getSender().getEmail());
                newChat.setRecipient(message.getRecipient().getEmail());
                newChat.setType("OneToOne");
                List<User> users = new ArrayList<>();
                users.add(message.getSender());
                users.add(message.getSender());

                //newChat.setUsers(users);
                newChat.setCreateAt(LocalDateTime.now().toString());
                newChat.setUpdatedAt(LocalDateTime.now().toString());
                Long chatId =  chatRepository.save(newChat).getId();
                typeMessage.setId(chatId);
            } else {
                List<Message> messages = new ArrayList<>();
                if (!chat.get().getMessages().isEmpty()) {
                    System.out.println("its not empty");
                    messages = chat.get().getMessages();
                } else {
                    System.out.println("its empty");
                }

                messages.add(messageService.toEntity(messageRequest));
                typeMessage.setId(chat.get().getId());
                chat.get().setMessages(messages);
                chat.get().setUpdatedAt(LocalDateTime.now().toString());
                chatRepository.save(chat.get());
            }
        }


//        }

        WebSocketSession recipientSession = userSessions.get(messageRequest.getRecipient());
        ObjectMapper objectMapper1 = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        if (recipientSession!=null && recipientSession.isOpen()){
            recipientSession.sendMessage(new TextMessage(objectMapper1.writeValueAsString(typeMessage)));
        }



        System.out.println("handleMessage");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("handleTransportError");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("afterConnectionClosed");
    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("supportsPartialMessages");
        return false;
    }
}
