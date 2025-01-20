package com.rdtm.system.service;

import com.rdtm.system.entity.Node;
import java.util.List;

public interface NodeService {
    // 基本 CRUD 操作
    Node createNode(String nodeName);
    Node getNodeById(Long id);
    List<Node> getAllNodes();
    Node updateNode(Node node);
    void deleteNode(Long id);
    
    // 信誉计算
    double calculatePrivateReputation(Node node);
    double calculatePublicReputation(Node node);
    double calculateComprehensiveTrust(Node source, Node target);
}