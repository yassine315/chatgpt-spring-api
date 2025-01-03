package com.chatgpt.service;

import com.chatgpt.dao.UserDao;
import com.chatgpt.dto.ChatCompletionRequest;
import com.chatgpt.dto.ChatCompletionResponse;
import com.chatgpt.dto.Message;
import com.chatgpt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatGptServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ChatGptServiceImpl chatGptService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(chatGptService, "baseUrl", "http://mockurl");
        ReflectionTestUtils.setField(chatGptService, "model", "mock-model");
    }


    public void testChat_NewConversation() {
        // Arrange
        String openaiToken = "mockToken";
        String promptText = "Hello, world!";
        String username = "testUser";
        Long conversationId = null;

        ChatCompletionResponse response = getChatCompletionResponse();

        when(restTemplate.postForObject(anyString(), any(ChatCompletionRequest.class), eq(ChatCompletionResponse.class)))
                .thenReturn(response);

        when(userDao.findByOpenaiToken(openaiToken)).thenReturn(null);

        // Act
        String actualResponse = chatGptService.chat(openaiToken, promptText, username, conversationId);

        // Assert
        assertEquals("Hello, user!", actualResponse);
        verify(userDao).save(any(User.class));
    }

    private static ChatCompletionResponse getChatCompletionResponse() {
        ChatCompletionResponse response = new ChatCompletionResponse();
        ChatCompletionResponse.Choice choice = new ChatCompletionResponse.Choice();
        Message message = new Message();
        message.setContent("Hello, user!");
        choice.setMessage(message);
        response.setChoices(List.of(choice));

        ChatCompletionResponse.Usage usage = new ChatCompletionResponse.Usage();
        usage.setPrompt_tokens(10);
        usage.setCompletion_tokens(10);
        response.setUsage(usage);

        response.setCreated(new Date());
        return response;
    }

}