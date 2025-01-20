package com.rdtm.system.controller;

import com.rdtm.system.entity.Node;
import com.rdtm.system.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @PostMapping
    public ResponseEntity<Node> createNode(@RequestParam String nodeName) {
        Node node = nodeService.createNode(nodeName);
        return ResponseEntity.ok(node);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Node> getNode(@PathVariable Long id) {
        Node node = nodeService.getNodeById(id);
        return ResponseEntity.ok(node);
    }

    @GetMapping
    public ResponseEntity<List<Node>> getAllNodes() {
        List<Node> nodes = nodeService.getAllNodes();
        return ResponseEntity.ok(nodes);
    }

    @GetMapping("/{id}/private-reputation")
    public ResponseEntity<Double> getPrivateReputation(@PathVariable Long id) {
        Node node = nodeService.getNodeById(id);
        double reputation = nodeService.calculatePrivateReputation(node);
        return ResponseEntity.ok(reputation);
    }

    @GetMapping("/{id}/public-reputation")
    public ResponseEntity<Double> getPublicReputation(@PathVariable Long id) {
        Node node = nodeService.getNodeById(id);
        double reputation = nodeService.calculatePublicReputation(node);
        return ResponseEntity.ok(reputation);
    }

    @GetMapping("/{sourceId}/trust/{targetId}")
    public ResponseEntity<Double> getComprehensiveTrust(
            @PathVariable Long sourceId,
            @PathVariable Long targetId) {
        Node source = nodeService.getNodeById(sourceId);
        Node target = nodeService.getNodeById(targetId);
        double trust = nodeService.calculateComprehensiveTrust(source, target);
        return ResponseEntity.ok(trust);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        nodeService.deleteNode(id);
        return ResponseEntity.ok().build();
    }
}