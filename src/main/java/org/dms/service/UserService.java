package org.dms.service;

import org.dms.dto.LoginRequest;
import org.dms.dto.RegisterRequest;
import org.dms.dto.Response;
import org.dms.dto.UsersDto;
import org.dms.entity.Users;


public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUser();
    Users getCurrentUser();
    Response updateUser(Long id, UsersDto usersDto);
    Response deleteUser(Long id);
    Response getUserDocuments(Long id);
}
