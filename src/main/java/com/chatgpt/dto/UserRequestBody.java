package com.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestBody {

    private String token;
    private String prompt;
    private String name;
    private Long conversationId; // optional


}
