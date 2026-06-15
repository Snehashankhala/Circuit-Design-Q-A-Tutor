package com.sneha.Circuit.repository;

import com.sneha.Circuit.model.CircuitQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CircuitRepository extends JpaRepository<CircuitQuestion, Long> {
    List<CircuitQuestion> findAllByOrderByCreatedAtDesc();
}