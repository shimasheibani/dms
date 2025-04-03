package dtosTest;

import org.dms.dto.UsersDto;
import org.dms.enums.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {
    @Test
    void testUserDto() {
        UsersDto usersDto = new UsersDto();
        usersDto.setUserRole(UserRole.User);
        usersDto.setEmail("shima@gmail.com");
        usersDto.setPassword("123456");

        assertEquals(usersDto.getEmail(), "shima@gmail.com");
        assertEquals(usersDto.getPassword(), "123456");
        assertEquals(usersDto.getUserRole(), UserRole.User);

    }
}
