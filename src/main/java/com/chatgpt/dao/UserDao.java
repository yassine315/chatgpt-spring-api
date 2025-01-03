package com.chatgpt.dao;

import com.chatgpt.models.Prompt;
import com.chatgpt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    User findByOpenaiToken(String openaiToken);
}
