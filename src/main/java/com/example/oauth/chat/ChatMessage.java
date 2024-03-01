package com.example.oauth.chat;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Builder
public class ChatMessage {

    private String content;

    private String sender;

    private MessageType Type;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", Type=" + Type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage)) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(getContent(), that.getContent()) && Objects.equals(getSender(), that.getSender()) && getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent(), getSender(), getType());
    }
}
