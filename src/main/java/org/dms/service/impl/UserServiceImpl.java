package org.dms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dms.dto.LoginRequest;
import org.dms.dto.RegisterRequest;
import org.dms.dto.Response;
import org.dms.dto.UsersDto;
import org.dms.entity.Users;
import org.dms.enums.UserRole;
import org.dms.exception.NotFoundException;
import org.dms.repository.UserRepository;
import org.dms.security.JwtUtils;
import org.dms.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final ModelMapper modelmapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole userRole = UserRole.ADMIN;
        if(registerRequest.getRole()!=null){
            userRole = registerRequest.getRole();
        }
        Users userToSave = Users.builder()
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getUsername())
                .userRole(userRole)
                .build();
        userRepository.save(userToSave);
        return Response.builder()
                .status(200)
                .message("User Registered Successfully")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        Users userToLogin = userRepository.findByEmail(loginRequest.getUsername()).orElseThrow(()->new RuntimeException("User Not Found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), userToLogin.getPassword())){
            throw new RuntimeException("Wrong Password");
        }
        String token = jwtUtils.generateToken(userToLogin.getEmail());
        return Response.builder()
                .status(200)
                .message("Login Success")
                .userRole(userToLogin.getUserRole())
                .Token(token)
                .expirationDate("3 Months")
                .build();
    }

    @Override
    public Response getAllUser() {
        List<Users> usersList = userRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<UsersDto> userDtoList = modelmapper.map(usersList, new TypeToken<UsersDto>() {}.getType());
        return Response.builder()
                .status(200)
                .message("All Users List Is as below:")
                .usersDtos(userDtoList)
                .build();
    }

    @Override
    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users currentUser = userRepository.findByEmail(username).orElseThrow(()-> new NotFoundException("user is not found"));
        currentUser.setDocumentsList(null);
        return currentUser;
    }

    @Override
    public Response updateUser(Long id, UsersDto usersDto) {
        Users updateUser = userRepository.findById(id).orElseThrow(()-> new NotFoundException("USer is not found"));
        if(usersDto.getEmail()!=null) updateUser.setEmail(usersDto.getEmail());
        if(usersDto.getUserRole()!=null) updateUser.setUserRole(usersDto.getUserRole());
        if(usersDto.getPassword()!=null) updateUser.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        userRepository.save(updateUser);
        return  Response.builder()
                .status(200)
                .message("user updated")
                .build();
    }

    @Override
    public Response deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(()-> new NotFoundException("user not found"));
        userRepository.deleteById(id);
        return  Response.builder()
                .status(200)
                .message("user deleted")
                .build();
    }

    @Override
    public Response getUserDocuments(Long id) {
        Users user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("user not found"));
        UsersDto usersDto = modelmapper.map(user, UsersDto.class);
        usersDto.getDocumentsDtos().forEach(documentsDto -> {
            documentsDto.setUserDto(null);
        });

        return Response.builder()
                .status(200)
                .message("suuccess")
                .usersDto(usersDto)
                .build();

    }
}
