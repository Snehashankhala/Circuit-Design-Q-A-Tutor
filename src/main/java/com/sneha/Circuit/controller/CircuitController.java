package com.sneha.Circuit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sneha.Circuit.model.CircuitQuestion;
import com.sneha.Circuit.service.AIService;
import com.sneha.Circuit.service.HistoryService;

@RestController
@RequestMapping("/api/circuit")
@CrossOrigin(origins = "*")
public class CircuitController {

    @Autowired
    private AIService aiService;

    @Autowired
    private HistoryService historyService;

    public static class QuestionRequest {
        public String question;
        public String topic;
    }

    @PostMapping("/ask")
    public ResponseEntity<CircuitQuestion> ask(@RequestBody QuestionRequest req) {
        String aiResponse = aiService.getCircuitExplanation(req.question);
        CircuitQuestion saved = historyService.save(req.question, aiResponse, req.topic);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/history")
    public ResponseEntity<List<CircuitQuestion>> history() {
        return ResponseEntity.ok(historyService.getAll());
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<CircuitQuestion> getOne(@PathVariable Long id) {
        return historyService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        historyService.delete(id);
        return ResponseEntity.noContent().build();
    }

   @PostMapping("/analyze-image")
public ResponseEntity<Map<String, String>> analyzeImage(
        @RequestParam("image") MultipartFile file) throws Exception {
    String result = aiService.analyzeCircuitImage(file);
    return ResponseEntity.ok(Map.of("analysis", result));
}
}