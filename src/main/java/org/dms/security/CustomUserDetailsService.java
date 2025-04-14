package org.dms.security;

import lombok.RequiredArgsConstructor;
import org.dms.entity.Users;
import org.dms.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user =  userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username));
        return AuthUser.builder().user(user).build();
    }
}
