package com.rdtm.system.service.impl;

import com.rdtm.system.entity.Node;
import com.rdtm.system.entity.Transaction;
import com.rdtm.system.repository.NodeRepository;
import com.rdtm.system.repository.TransactionRepository;
import com.rdtm.system.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NodeServiceImpl implements NodeService {
    
    @Autowired
    private NodeRepository nodeRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Node createNode(String nodeName) {
        Node node = new Node();
        node.setNodeName(nodeName);
        return nodeRepository.save(node);
    }

    @Override
    public Node getNodeById(Long id) {
        return nodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Node not found"));
    }

    @Override
    public List<Node> getAllNodes() {
        return nodeRepository.findAll();
    }

    @Override
    public Node updateNode(Node node) {
        return nodeRepository.save(node);
    }

    @Override
    public void deleteNode(Long id) {
        nodeRepository.deleteById(id);
    }

    @Override
    public double calculatePrivateReputation(Node node) {
        List<Transaction> transactions = transactionRepository.findBySourceNode(node);
        if (transactions.isEmpty()) {
            return 0.5; // 默认信誉值
        }

        double weightedSum = 0;
        double totalWeight = 0;
        
        for (Transaction tx : transactions) {
            double weight = calculateWeight(tx.getTimestamp(), tx.getAmount());
            weightedSum += weight * tx.getEvaluationScore();
            totalWeight += weight;
        }

        return weightedSum / totalWeight;
    }

    @Override
    public double calculatePublicReputation(Node node) {
        List<Node> allNodes = nodeRepository.findAll();
        if (allNodes.size() <= 1) {
            return 0.5; // 默认信誉值
        }

        double totalReputation = 0;
        int count = 0;
        
        for (Node otherNode : allNodes) {
            if (!otherNode.getId().equals(node.getId())) {
                double similarity = calculateSimilarity(node, otherNode);
                totalReputation += similarity * otherNode.getPrivateReputation();
                count++;
            }
        }

        return count > 0 ? totalReputation / count : 0.5;
    }

    @Override
    public double calculateComprehensiveTrust(Node source, Node target) {
        double privateReputation = calculatePrivateReputation(target);
        double publicReputation = calculatePublicReputation(target);
        double lambda = calculateConfidenceFactor(source, target);

        return lambda * publicReputation + (1 - lambda) * privateReputation;
    }

    private double calculateWeight(LocalDateTime timestamp, double amount) {
        long daysSinceTransaction = ChronoUnit.DAYS.between(timestamp, LocalDateTime.now());
        return (1.0 / (1 + daysSinceTransaction)) * Math.log1p(amount);
    }

    private double calculateSimilarity(Node a, Node b) {
        // 简单实现，可以根据需求扩展
        return 0.8;
    }

    private double calculateConfidenceFactor(Node source, Node target) {
        long transactionCount = transactionRepository.countBySourceNodeAndTargetNode(source, target);
        return Math.min(1.0, Math.log1p(transactionCount) / 10);
    }
}