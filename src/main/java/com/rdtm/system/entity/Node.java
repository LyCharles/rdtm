package com.rdtm.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "nodes")
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nodeName;
    private Double privateReputation = 0.5;
    private Double publicReputation = 0.5;
    private Double confidenceFactor = 0.5;

    @OneToMany(mappedBy = "sourceNode")
    private List<Transaction> sourceTransactions;

    @OneToMany(mappedBy = "targetNode")
    private List<Transaction> targetTransactions;
}