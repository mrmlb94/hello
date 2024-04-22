package com.userhello.hello.repository;

import com.userhello.hello.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    Optional<User> findByUname(String uname);
    List<User> findAllByOrderByNameAsc();
}


