package com.userhello.hello.integration;

import com.userhello.hello.Service.UserService;
import com.userhello.hello.model.User;
import com.userhello.hello.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User john;
    private User alice;
    private User bob;

    @BeforeEach
    void setUp() {
        john = new User(1L, "John", "johnny", "Doe", null, null, null, null, null, null, null, "john.doe@example.com");
        alice = new User(2L, "Alice", "alice", "Wonder", null, null, null, null, null, null, null, "alice.wonder@example.com");
        bob = new User(3L, "Bob", "bobby", "Builder", null, null, null, null, null, null, null, "bob.builder@example.com");
    }

    @Test
    void testSignUpSuccess() {
        User newUser = new User("newuser");
        when(userRepository.findByUname("newuser")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User signedUpUser = userService.signUp(newUser);

        assertNotNull(signedUpUser);
        verify(userRepository).save(newUser);
    }

    @Test
    void testSignUpFailure() {
        User existingUser = new User("existinguser");
        when(userRepository.findByUname("existinguser")).thenReturn(Optional.of(existingUser));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.signUp(existingUser);
        });

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(john));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(1L);

        assertFalse(result.isPresent());
        verify(userRepository).findById(1L);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.save(john)).thenReturn(john);

        User updatedUser = userService.updateUser(john);

        assertNotNull(updatedUser);
        assertEquals("John", updatedUser.getName());
        verify(userRepository).save(john);
    }

    @Test
    void testFindAllUsers() {
        List<User> userList = Arrays.asList(john, alice);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void testFindByName() {
        List<User> userList = Collections.singletonList(john);
        when(userRepository.findByName("John")).thenReturn(userList);

        List<User> result = userService.findByName("John");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("John", result.get(0).getName());
        verify(userRepository).findByName("John");
    }

    @Test
    void testFindByNameEmptyResult() {
        when(userRepository.findByName("NonexistentUser")).thenReturn(Collections.emptyList());

        List<User> users = userService.findByName("NonexistentUser");

        assertTrue(users.isEmpty());
        verify(userRepository).findByName("NonexistentUser");
    }

    @Test
    void testFindByNameNullInput() {
        List<User> result = userService.findByName(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findByName(null);
    }

    @Test
    void testFindByUname() {
        when(userRepository.findByUname("johndoe")).thenReturn(Optional.of(john));

        Optional<User> foundUser = userService.findByUname("johndoe");

        assertTrue(foundUser.isPresent());
        assertEquals("johnny", foundUser.get().getUname());
        verify(userRepository).findByUname("johndoe");
    }

    @Test
    void testFindByUnameNotFound() {
        when(userRepository.findByUname("nonexistent")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findByUname("nonexistent");

        assertFalse(foundUser.isPresent());
        verify(userRepository).findByUname("nonexistent");
    }

    @Test
    void testFindAllUsersSortedByName() {
        List<User> userList = Arrays.asList(alice, bob, john);
        when(userRepository.findAllByOrderByNameAsc()).thenReturn(userList);

        List<User> result = userService.findAllUsersSortedByName();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("John", result.get(2).getName());
        verify(userRepository).findAllByOrderByNameAsc();
    }

    @Test
    void testFindAllUsersSortedByNameEmptyResult() {
        when(userRepository.findAllByOrderByNameAsc()).thenReturn(Collections.emptyList());

        List<User> users = userService.findAllUsersSortedByName();

        assertTrue(users.isEmpty());
        verify(userRepository).findAllByOrderByNameAsc();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(john));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("John", foundUser.getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User foundUser = userService.getUserById(1L);

        assertNull(foundUser);
        verify(userRepository).findById(1L);
    }
}
