package com.rdtm.system.service.impl;

import com.rdtm.system.entity.Node;
import com.rdtm.system.entity.Transaction;
import com.rdtm.system.repository.TransactionRepository;
import com.rdtm.system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        // 设置交易时间
        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(LocalDateTime.now());
        }
        
        // 验证评分范围
        if (transaction.getEvaluationScore() < 0 || transaction.getEvaluationScore() > 1) {
            throw new IllegalArgumentException("Evaluation score must be between 0 and 1");
        }
        
        // 验证交易金额
        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
        
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsBySourceNode(Node sourceNode) {
        return transactionRepository.findBySourceNode(sourceNode);
    }

    @Override
    public List<Transaction> getTransactionsByTargetNode(Node targetNode) {
        return transactionRepository.findByTargetNode(targetNode);
    }

    @Override
    public List<Transaction> getTransactionsBetweenNodes(Node sourceNode, Node targetNode) {
        return transactionRepository.findBySourceNodeAndTargetNode(sourceNode, targetNode);
    }

    @Override
    public long countTransactionsBetweenNodes(Node sourceNode, Node targetNode) {
        return transactionRepository.countBySourceNodeAndTargetNode(sourceNode, targetNode);
    }
}