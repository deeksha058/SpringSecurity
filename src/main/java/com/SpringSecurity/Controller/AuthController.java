package com.SpringSecurity.Controller;

import com.SpringSecurity.Entity.User;
import com.SpringSecurity.config.AuthRequest;
import com.SpringSecurity.config.AuthResponse;
import com.SpringSecurity.jwt.JwtTokenUtil;
import com.SpringSecurity.repository.UserRepository;
import com.SpringSecurity.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Api(value = "CRUD Rest APIs for Post resources")
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired AuthenticationManager authManager;
	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
		try {

			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getEmail(), request.getPassword())
			);

			User user = (User) authentication.getPrincipal();
			String accessToken = jwtUtil.generateAccessToken(user);
			AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
			
			return ResponseEntity.ok().body(response);
			
		} catch (BadCredentialsException ex) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody @Valid AuthRequest request) {
//		try {
            User user1 = new User();
			user1.setEmail(request.getEmail());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String password = passwordEncoder.encode(request.getPassword());
			user1.setPassword(password);
//			userRepository.save(user1);
			return userService.saveUser(user1);

//		} catch (BadCredentialsException ex) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return  new ResponseEntity<>("Signup Successfully!", HttpStatus.OK);
	}

	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> confirmUserAccount(@RequestParam(required=false,name="token")String confirmationToken) {
		return userService.confirmEmail(confirmationToken);
	}
}
