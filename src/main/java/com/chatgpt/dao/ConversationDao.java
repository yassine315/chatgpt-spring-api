package com.chatgpt.dao;

import com.chatgpt.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationDao extends JpaRepository<Conversation,Long> {
}
