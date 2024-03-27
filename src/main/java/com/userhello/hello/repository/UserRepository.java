package com.userhello.hello.repository;
import com.userhello.hello.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
