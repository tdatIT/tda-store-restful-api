package com.webapp.tdastore.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Conversations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member1_id")
    private User member1Id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member2_id")
    private User member2Id;
    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY)
    private List<Message> messages;
}
