package com.rdtm.system.repository;

import com.rdtm.system.entity.Node;
import com.rdtm.system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // 查找源节点的所有交易
    List<Transaction> findBySourceNode(Node sourceNode);
    
    // 查找目标节点的所有交易
    List<Transaction> findByTargetNode(Node targetNode);
    
    // 查找两个节点之间的所有交易
    List<Transaction> findBySourceNodeAndTargetNode(Node sourceNode, Node targetNode);
    
    // 统计两个节点之间的交易数量
    long countBySourceNodeAndTargetNode(Node sourceNode, Node targetNode);
}