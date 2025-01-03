package com.chatgpt.dao;

import com.chatgpt.models.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptDao extends JpaRepository<Prompt,Long> {
}
