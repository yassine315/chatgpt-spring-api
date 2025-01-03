package com.chatgpt.service;

import com.chatgpt.dao.ConversationDao;
import com.chatgpt.dao.UserDao;
import com.chatgpt.dto.ChatCompletionRequest;
import com.chatgpt.dto.ChatCompletionResponse;
import com.chatgpt.models.Conversation;
import com.chatgpt.models.Prompt;
import com.chatgpt.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGptServiceImpl implements ChatGptService {

    private final UserDao userDao;
    private final ConversationDao conversationDao;

    @Value("${openai.chatgpt.url}")
    private String baseUrl;

    @Value("${openai.model}")
    private String model;

    public String chat(String openaiToken, String promptText , String username, Long conversationId) {

        RestTemplate restTemplate = getRestTemplateWithTokenSet(openaiToken);

        ChatCompletionRequest request = new ChatCompletionRequest(promptText, model);

        ChatCompletionResponse response = restTemplate.postForObject(baseUrl, request, ChatCompletionResponse.class);

        if(response != null) {
           Prompt prompt = Prompt.builder()
                   .question(promptText)
                   .response(response.getChoices().get(0).getMessage().getContent())
                   .createdAt(response.getCreated())
                   .build();

           Conversation conversation;

           if(conversationId != null) {
               conversation = conversationDao.findById(conversationId).get();
               prompt.setConversation(conversation);
               conversation.getPrompts().add(prompt);

               conversationDao.save(conversation);

           }
           else {
               conversation = Conversation.builder()
                       .createdAt(response.getCreated())
                       .promptTokens(response.getUsage().getPrompt_tokens())
                       .completionTokens(response.getUsage().getCompletion_tokens())
                       .model(model)
                       .build();

               prompt.setConversation(conversation);

               List<Prompt> prompts = new ArrayList<>();
               prompts.add(prompt);

               conversation.setPrompts(prompts);

               var user = userDao.findByOpenaiToken(openaiToken);

               if(user != null) {
                   user.getConversations().add(conversation);

               } else {
                   List<Conversation> conversations = new ArrayList<>();
                   conversations.add(conversation);

                   user = User.builder()
                           .username(username)
                           .conversations(conversations)
                           .openaiToken(openaiToken)
                           .build();
               }

               conversation.setUser(user);
               userDao.save(user);
           }


            return response.getChoices().get(0).getMessage().getContent();
        }

        return null;
    }

    public List<Conversation> conversations(String openAiToken) {
        var user =  userDao.findByOpenaiToken(openAiToken);
        return user.getConversations();
    }

    private RestTemplate getRestTemplateWithTokenSet(String openaiToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiToken);
            return execution.execute(request,body);
        }));
        return restTemplate;
    }

}
