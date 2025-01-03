package com.chatgpt.service;

import com.chatgpt.models.Conversation;

import java.util.List;

public interface ChatGptService {

    String chat(String openaiToken, String promptText , String username, Long conversationId);

    List<Conversation> conversations(String openaiToken);

}
