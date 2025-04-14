<<<<<<< HEAD
package org.dms.dtos;
=======
package org.dms.dtosTest;
>>>>>>> bee2c3d2fefd102ba4b57642e6b421d97e0ffd8c
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
