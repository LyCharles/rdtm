package com.rdtm.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_node_id")
    private Node sourceNode;

    @ManyToOne
    @JoinColumn(name = "target_node_id")
    private Node targetNode;

    private Double amount;
    private Double evaluationScore;
    private LocalDateTime timestamp;
}