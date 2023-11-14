package com.Alif.ChatAppX.controller;

import com.Alif.ChatAppX.dto.message.GroupRequest;
import com.Alif.ChatAppX.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/createGroup")
    public void createGroup(@RequestBody GroupRequest groupRequest){
        messageService.createGroup(groupRequest);
    }
}
