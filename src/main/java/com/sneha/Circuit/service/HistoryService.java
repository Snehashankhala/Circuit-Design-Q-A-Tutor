package com.sneha.Circuit.service;

import com.sneha.Circuit.model.CircuitQuestion;
import com.sneha.Circuit.repository.CircuitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    @Autowired
    private CircuitRepository repository;

    public CircuitQuestion save(String question, String response, String topic) {
        CircuitQuestion q = new CircuitQuestion();
        q.setQuestion(question);
        q.setAiResponse(response);
        q.setTopic(topic);
        return repository.save(q);
    }

    public List<CircuitQuestion> getAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<CircuitQuestion> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}