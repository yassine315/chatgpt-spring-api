package com.chatgpt.controller;

import com.chatgpt.dto.UserRequestBodyDto;
import com.chatgpt.service.ChatGptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatGPTController {

    final private ChatGptService service;

    @PostMapping("/prompts")
    public ResponseEntity<String> chat(@RequestBody UserRequestBodyDto request) {

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
