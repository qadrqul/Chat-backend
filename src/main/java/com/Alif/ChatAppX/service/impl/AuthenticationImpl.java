package com.Alif.ChatAppX.service.impl;

import com.Alif.ChatAppX.dto.RegisterRequest;
import com.Alif.ChatAppX.dto.UserResponse;
import com.Alif.ChatAppX.dto.auth.AuthenticationRequest;
import com.Alif.ChatAppX.dto.auth.AuthenticationResponse;
import com.Alif.ChatAppX.entities.User;
import com.Alif.ChatAppX.enums.Role;
import com.Alif.ChatAppX.repository.UserRepository;
import com.Alif.ChatAppX.security.JwtTokenProvider;
import com.Alif.ChatAppX.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import com.Alif.ChatAppX.exception.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Optional<User> user1 = userRepository.findByEmailOrNickname(request.getEmail(), request.getEmail());
        if (user1.isEmpty())
            throw new BadCredentialsException("the nickname and email ");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user1.get().getEmail(), // Передайте значение email или никнейма
                    request.getPassword()));
        } catch (AuthenticationException e) {
            // Обработка ошибки аутентификации, например, неверный email или пароль
            throw new BadCredentialsException("Authentication failed: " + e.getMessage());
        }


        User user = userRepository.findByEmailOrNickname(request.getEmail(), request.getEmail()).orElseThrow(() -> new BadCredentialsException("User not found"));
        String token = jwtTokenProvider.createToken(user.getEmail(), userRepository.findByEmail(user.getEmail()).get().getRole());

        return AuthenticationResponse.builder()
                .user(convertToresponse(user))
                .accessToken(token)
                .build();
    }


    @Override
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        System.out.println("called2");

        checkUsernameIsExist(request.getEmail(), request.getNickname());
        System.out.println("called3");

        User user = new User();
        user.setEmail(request.getEmail());
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setNickname(request.getNickname());
        user.setRole(Role.USER);
        System.out.println("called4");

        
        userRepository.save(user);
        System.out.println("called5");

        return ResponseEntity.ok(convertAuthentication(user));
    }

    private AuthenticationResponse convertAuthentication(User user) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUser(convertToresponse(user));
        String token = jwtTokenProvider.createToken(user.getEmail(), userRepository.findByEmail(user.getEmail()).get().getRole());
        response.setAccessToken(token);
        System.out.println("called8");

        return response;
    }

    private UserResponse convertToresponse(User user) {
        System.out.println("called6");

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setNickname(user.getNickname());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        System.out.println("called7");

        return userResponse;
    }

    private void checkUsernameIsExist(String email, String nickname) {
        if (userRepository.findByEmail(email).isPresent()){
            throw new BadCredentialsException("user with this email is already exists!");
        }
        if(userRepository.findByNickname(nickname).isPresent()){
            throw new BadCredentialsException("user with this nickname is already exists!");
         }
    }

}
