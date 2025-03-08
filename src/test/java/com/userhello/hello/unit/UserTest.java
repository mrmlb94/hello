package com.userhello.hello.unit;


import org.junit.jupiter.api.Test;

import com.userhello.hello.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserConstructorWithUname() {
        String uname = "johnny";
        User user = new User(uname);

        assertNotNull(user);
        assertEquals(uname, user.getUname());
    }
}
