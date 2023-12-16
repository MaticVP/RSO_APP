package com.example.RSO.Service.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.RSO.Service.Entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(@Param("username") String username);
    List<User> removeByUsername(@Param("username") String username);
}