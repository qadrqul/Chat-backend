package com.Alif.ChatAppX.repository;

import com.Alif.ChatAppX.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findBySenderAndRecipient(String sender, String recipient);
    Optional<Chat> findBySenderAndRecipientOrRecipientAndSender(String sender, String recipient,
    String recipient2, String sender1);

}
