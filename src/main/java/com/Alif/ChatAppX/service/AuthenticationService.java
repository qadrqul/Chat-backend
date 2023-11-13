package com.Alif.ChatAppX.service;


import com.Alif.ChatAppX.dto.RegisterRequest;
import com.Alif.ChatAppX.dto.auth.AuthenticationResponse;
import com.Alif.ChatAppX.dto.auth.AuthenticationRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

}
