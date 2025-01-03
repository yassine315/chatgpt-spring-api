package com.chatgpt.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatCompletionRequestDto {

    private String model;
    private List<MessageDto> messages;

    public ChatCompletionRequestDto(String prompt, String model) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new MessageDto("user", prompt));
    }
}
