package org.dms.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.dms.dto.LoginRequest;
import org.dms.dto.RegisterRequest;
import org.dms.dto.Response;
import org.dms.dto.UsersDto;
import org.dms.entity.Users;
import org.dms.enums.UserRole;
import org.dms.repository.UserRepository;
import org.dms.security.JwtUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Regression tests for UserServiceImpl.
 *
 * Main bug covered here: getAllUser() mapped the user list with
 * new TypeToken<UsersDto>(){}.getType() instead of
 * new TypeToken<List<UsersDto>>(){}.getType() — a single-item type token
 * for a list source, which ModelMapper cannot map correctly.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private ModelMapper modelmapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void registerUser_encodesPassword_andDefaultsRoleToAdmin_whenRoleMissing() {
        RegisterRequest request = new RegisterRequest("shima", "sheibani", "shima@example.com", "plainPass", null);
        when(passwordEncoder.encode("plainPass")).thenReturn("encoded-pass");

        userServiceImpl.registerUser(request);

        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository).save(captor.capture());
        assertEquals("encoded-pass", captor.getValue().getPassword());
        assertEquals(UserRole.ADMIN, captor.getValue().getUserRole());
        assertEquals("shima@example.com", captor.getValue().getEmail());
    }

    @Test
    void loginUser_throws_whenPasswordDoesNotMatch() {
        Users user = Users.builder().email("shima@example.com").password("hashed").build();
        when(userRepository.findByEmail("shima@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);

        LoginRequest request = new LoginRequest("shima@example.com", "wrong");

        assertThrows(RuntimeException.class, () -> userServiceImpl.loginUser(request));
        verify(jwtUtils, never()).generateToken(any());
    }

    @Test
    void loginUser_returnsToken_whenCredentialsAreCorrect() {
        Users user = Users.builder().email("shima@example.com").password("hashed").userRole(UserRole.ADMIN).build();
        when(userRepository.findByEmail("shima@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("correct", "hashed")).thenReturn(true);
        when(jwtUtils.generateToken("shima@example.com")).thenReturn("jwt-token");

        Response response = userServiceImpl.loginUser(new LoginRequest("shima@example.com", "correct"));

        assertEquals("jwt-token", response.getToken());
        assertEquals(UserRole.ADMIN, response.getUserRole());
    }

    @Test
    void getAllUser_mapsRepositoryListToDtoList() {
        List<Users> users = List.of(Users.builder().email("a@example.com").build());
        when(userRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(users);

        List<UsersDto> mapped = new ArrayList<>();
        UsersDto dto = new UsersDto();
        dto.setEmail("a@example.com");
        mapped.add(dto);
        when(modelmapper.map(eq(users), any(java.lang.reflect.Type.class))).thenReturn(mapped);

        Response response = userServiceImpl.getAllUser();

        assertNotNull(response.getUsersDtos(), "usersDtos must be populated, not null/empty due to a wrong TypeToken");
        assertEquals(1, response.getUsersDtos().size());
        assertEquals("a@example.com", response.getUsersDtos().get(0).getEmail());
    }
}
