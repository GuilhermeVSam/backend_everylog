package com.everylog.Everylog.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.everylog.Everylog.model.ReviewModel;

public interface ReviewRepository extends CrudRepository<ReviewModel, Integer> {
    Optional<ReviewModel> findById(int id);

    List<ReviewModel> findByUsername(String username);
}