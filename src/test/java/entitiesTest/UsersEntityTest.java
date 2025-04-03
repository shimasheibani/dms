package entitiesTest;

import org.dms.entity.Users;
import org.dms.enums.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersEntityTest {
    @Test
    void testUserEntity(){
        Users user = new Users();
        user.setEmail("shima@gmail.com");
        user.setUserRole(UserRole.User);

        assertEquals("shima@gmail.com", user.getEmail());
        assertEquals(UserRole.User, user.getUserRole());
    }
}
