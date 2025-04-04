package org.dms.repositoryTest;

import org.dms.entity.Users;
import org.dms.enums.UserRole;
import org.dms.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@RequiredArgsConstructor
public class UserDepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Users userTest;
    @BeforeAll
    void setup() {
        assertNotNull(userRepository);
    }
    @BeforeEach
    void init(){
        userRepository.deleteAll();
        userTest = new Users();
        userTest.setUserRole(UserRole.ADMIN);
        userTest.setEmail("shima@gmail.com");
        userTest.setPassword("123456");
        userTest.setName("shima");
        userTest.setLastName("sheibani");
    }

    @Test
    void testSaveUser(){

        Users savedUser = userRepository.save(userTest);
        assertNotNull(savedUser.getId());
        assertEquals("shima@gmail.com", savedUser.getEmail());
        assertEquals("123456", savedUser.getPassword());
        assertEquals("shima", savedUser.getName());
        assertEquals("sheibani", savedUser.getLastName());
    }
}
