package com.chatgpt.controller;

import com.chatgpt.dto.UserRequestBody;
import com.chatgpt.models.Conversation;
import com.chatgpt.service.ChatGptService;
import com.chatgpt.service.ChatGptServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatGPTController {

    final private ChatGptService service;

    @PostMapping("/prompts")
    public ResponseEntity<String> chat(@RequestBody UserRequestBody request) {
        System.out.println("call Prompts");
        // service call
        String response = service.chat(
                request.getToken(), request.getPrompt(), request.getName(), request.getConversationId()
        );
        if(response != null) {
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity<>(
                null,
                HttpStatus.BAD_REQUEST
        );
    }

}
