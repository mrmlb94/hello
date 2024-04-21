package com.userhello.hello.Service;

import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(User user) {
        Optional<User> existingUser = userRepository.findByUname(user.getUname());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(user);
    }


    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public Optional<User> findByUname(String uname) {
        return userRepository.findByUname(uname);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<User> findAllUsersSortedByName() {
        return userRepository.findAllByOrderByNameAsc();
    }
}
