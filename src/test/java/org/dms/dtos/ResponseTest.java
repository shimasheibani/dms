<<<<<<< HEAD
package org.dms.dtos;
=======
package org.dms.dtosTest;
>>>>>>> bee2c3d2fefd102ba4b57642e6b421d97e0ffd8c

import org.dms.dto.Response;
import org.dms.dto.UsersDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {
    @Test
    void testResponse() {
        Response response = Response.builder()
                .status(200)
                .message("Success")
                .usersDto(new UsersDto())
                .build();
        assertEquals(200, response.getStatus());
        assertEquals("Success", response.getMessage());
        assertEquals(new UsersDto(), response.getUsersDto());
    }
}
