package org.dms.dtosTest;
import org.dms.dto.LoginRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {
    @Test
    void testLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin");
        assertEquals("admin", loginRequest.getUsername());
        assertEquals(loginRequest.getPassword(), "admin");
    }
}
