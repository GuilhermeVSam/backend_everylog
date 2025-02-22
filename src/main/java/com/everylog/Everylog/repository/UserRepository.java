package com.everylog.Everylog.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.everylog.Everylog.model.UserModel;
import java.util.List;

public interface UserRepository extends CrudRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
