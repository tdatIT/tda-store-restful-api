package com.webapp.tdastore.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conversation_id",nullable = false)
    private Conversations conversation;
    @ManyToOne
    @JoinColumn(name = "sender_id",nullable = false)
    private User sender;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(nullable = false)
    private Timestamp sendAt;


}
