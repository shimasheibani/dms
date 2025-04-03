package org.dms.repository;

import org.dms.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long>{
    Optional<Users> findByEmail(String email);
}
