package com.userhello.hello.unit.Service;

import com.userhello.hello.service.UserService;
import com.userhello.hello.model.User;
import com.userhello.hello.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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
        User newUser = new User("existinguser");
        when(userRepository.findByUname("existinguser")).thenReturn(Optional.of(newUser));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.signUp(newUser);
        });

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void testFindById() {
        Optional<User> foundUser = Optional.of(new User(1L, "John", "johnny", "Doe", null, null, null, null, null, null, null, "john.doe@example.com"));
        when(userRepository.findById(1L)).thenReturn(foundUser);

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
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
        User user = new User(1L, "John", "johnny", "Doe", null, null, null, null, null, null, null, "john.doe@example.com");
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals("John", updatedUser.getName());
        verify(userRepository).save(user);
    }

    @Test
    void testFindAllUsers() {
        List<User> userList = Arrays.asList(new User("user1"), new User("user2"));
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.findAllUsers();

        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void testFindAllUsersSortedByName() {
        List<User> userList = Arrays.asList(new User("user1"), new User("user2"));
        when(userRepository.findAllByOrderByNameAsc()).thenReturn(userList);

        List<User> users = userService.findAllUsersSortedByName();

        assertEquals(2, users.size());
        verify(userRepository).findAllByOrderByNameAsc();
    }
}
