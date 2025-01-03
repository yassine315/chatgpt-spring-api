package com.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionResponse {

    private List<Choice> choices;
    private String model;
    private Date created;
    private Usage usage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
      private int index;
      private Message message;
    }
}
