package com.everylog.Everylog.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.everylog.Everylog.model.UserModel;

public interface UserRepository extends CrudRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);
}
