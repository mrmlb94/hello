package com.userhello.hello.unit;


import com.userhello.hello.exception.UsernameAlreadyExistsException;
import com.userhello.hello.model.User;
import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.service.UserService;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User ali;
    private User alice;
    private User bob;

    @BeforeEach
    void setUp() {
        ali = new User.Builder()
                .setId(1L)
                .setName("Ali")
                .setUname("Aliny")
                .setFamilyName("Ahmadi")
                .setEmail("Ali.Ahmadi@example.com")
                .build();

        alice = new User.Builder()
                .setId(2L)
                .setName("Alice")
                .setUname("alice")
                .setFamilyName("Wonder")
                .setEmail("alice.wonder@example.com")
                .build();

        bob = new User.Builder()
                .setId(3L)
                .setName("Bob")
                .setUname("bobby")
                .setFamilyName("Builder")
                .setEmail("bob.builder@example.com")
                .build();
    }

    @Test
    void testSignUpSuccess() {
        User newUser = new User.Builder().setUname("newuser").build();
        when(userRepository.findByUname("newuser")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User signedUpUser = userService.signUp(newUser);

        assertNotNull(signedUpUser);
        assertEquals("newuser", signedUpUser.getUname());
        verify(userRepository).save(newUser);
    }

    @Test
    void testSignUpUsernameAlreadyExists() {
        User existingUser = new User.Builder().setUname("existingUser").build();
        when(userRepository.findByUname("existingUser")).thenReturn(Optional.of(existingUser));

        UsernameAlreadyExistsException exception = assertThrows(
                UsernameAlreadyExistsException.class,
                () -> userService.signUp(existingUser)
        );

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(existingUser);
    }

    @Test
    void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(ali));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Ali", result.get().getName());
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
        when(userRepository.save(ali)).thenReturn(ali);

        User updatedUser = userService.updateUser(ali);

        assertNotNull(updatedUser);
        assertEquals("Ali", updatedUser.getName());
        verify(userRepository).save(ali);
    }

    @Test
    void testFindByName() {
        List<User> userList = Collections.singletonList(ali);
        when(userRepository.findByName("Ali")).thenReturn(userList);

        List<User> result = userService.findByName("Ali");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Ali", result.get(0).getName());
        verify(userRepository).findByName("Ali");
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
        when(userRepository.findByUname("AliAhmadi")).thenReturn(Optional.of(ali));

        Optional<User> foundUser = userService.findByUname("AliAhmadi");

        assertTrue(foundUser.isPresent());
        assertEquals("Aliny", foundUser.get().getUname());
        verify(userRepository).findByUname("AliAhmadi");
    }

    @Test
    void testFindByUnameNotFound() {
        when(userRepository.findByUname("nonexistent")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findByUname("nonexistent");

        assertFalse(foundUser.isPresent());
        verify(userRepository).findByUname("nonexistent");
    }

    @Test
    void testFindAllUsers() {
        List<User> userList = Arrays.asList(ali, alice);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.findAllUsers();

        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void testFindAllUsersSortedByName() {
        List<User> userList = Arrays.asList(alice, bob, ali);
        when(userRepository.findAllByOrderByNameAsc()).thenReturn(userList);

        List<User> result = userService.findAllUsersSortedByName();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Ali", result.get(2).getName());
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
        when(userRepository.findById(1L)).thenReturn(Optional.of(ali));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("Ali", foundUser.getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User foundUser = userService.getUserById(1L);

        assertNull(foundUser);
        verify(userRepository).findById(1L);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(ali)).thenReturn(ali);

        User createdUser = userService.createUser(ali);

        assertNotNull(createdUser);
        assertEquals("Ali", createdUser.getName());
        verify(userRepository).save(ali);
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = Arrays.asList(ali, alice, bob);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Ali", result.get(0).getName());
        assertEquals("Alice", result.get(1).getName());
        assertEquals("Bob", result.get(2).getName());
        verify(userRepository).findAll();
    }

}
