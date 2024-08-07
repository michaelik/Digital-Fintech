package com.example.service;

import com.example.dtos.request.SignInRequestDTO;
import com.example.dtos.request.SignUpDTO;
import com.example.dtos.response.SignInResponseDTO;
import com.example.dtos.response.UserDTO;
import org.springframework.stereotype.Component;

public interface UserService {
    void signUp(SignUpDTO request);
    SignInResponseDTO signIn(SignInRequestDTO request);

    UserDTO getUserById(long userId);


}
