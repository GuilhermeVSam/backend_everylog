package com.everylog.Everylog.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.everylog.Everylog.model.UserModel;

public interface UserRepository extends CrudRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByEmail(String email);

    UserModel findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
