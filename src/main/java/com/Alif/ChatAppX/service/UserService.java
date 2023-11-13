package com.Alif.ChatAppX.service;

import com.Alif.ChatAppX.entities.User;

public interface UserService {
    User getUserFromToken(String token);
}
