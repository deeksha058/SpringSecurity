package com.SpringSecurity.service;

import com.SpringSecurity.Entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> saveUser(User user);

    ResponseEntity<?> confirmEmail(String confirmationToken);
}
