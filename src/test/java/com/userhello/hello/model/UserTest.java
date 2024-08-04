package com.userhello.hello.model;

import org.junit.jupiter.api.Test;
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
