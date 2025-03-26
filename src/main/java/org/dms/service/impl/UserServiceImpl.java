package org.dms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dms.repository.UserRepository;
import org.dms.security.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final JwtUtils jwtUtils;
    private final ModelMapper modelmapper;
    private final UserRepository userRepository;

}
