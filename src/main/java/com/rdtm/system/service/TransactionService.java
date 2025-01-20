package com.rdtm.system.service;

import com.rdtm.system.entity.Node;
import com.rdtm.system.entity.Transaction;
import java.util.List;

public interface TransactionService {
    // 基本 CRUD 操作
    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    
    // 特定查询方法
    List<Transaction> getTransactionsBySourceNode(Node sourceNode);
    List<Transaction> getTransactionsByTargetNode(Node targetNode);
    List<Transaction> getTransactionsBetweenNodes(Node sourceNode, Node targetNode);
    long countTransactionsBetweenNodes(Node sourceNode, Node targetNode);
}