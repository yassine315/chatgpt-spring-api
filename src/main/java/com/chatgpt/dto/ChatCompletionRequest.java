package com.chatgpt.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatCompletionRequest {

    private String model;
    private List<Message> messages;

    public ChatCompletionRequest(String prompt, String model) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}
