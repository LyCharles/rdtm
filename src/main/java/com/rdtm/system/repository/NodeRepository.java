package com.rdtm.system.repository;

import com.rdtm.system.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
    // 根据节点名称查找节点
    Node findByNodeName(String nodeName);
    
    // 检查节点名称是否存在
    boolean existsByNodeName(String nodeName);
}