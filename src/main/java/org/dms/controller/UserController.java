package org.dms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dms.dto.RegisterRequest;
import org.dms.dto.Response;
import org.dms.dto.UsersDto;
import org.dms.entity.Users;
import org.dms.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Configuration
@RequestMapping("api/user")
//@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<Response> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUser());
    }
    @GetMapping("/currentuser")
    public ResponseEntity<Users> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUser());
    }
    @PutMapping("update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody @Valid UsersDto userDto){
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }
    @DeleteMapping("/{id")
    public ResponseEntity<Response> deleteUsr(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
    @GetMapping("/userDocument")
    public ResponseEntity<Response> getUserDocuments(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserDocuments(id));
    }
}
