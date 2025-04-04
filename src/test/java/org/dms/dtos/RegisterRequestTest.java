package org.dms.dtosTest;
import org.dms.dto.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class RegisterRequestTest {
    @Test
    void registerRequest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setLastName("shima#gmail.com");
        registerRequest.setName("shima");
        registerRequest.setPassword("1234456788");
        assertEquals("shima#gmail.com", registerRequest.getLastName());
        assertEquals("shima", registerRequest.getName());
        assertEquals("1234456788", registerRequest.getPassword());
    }
}
