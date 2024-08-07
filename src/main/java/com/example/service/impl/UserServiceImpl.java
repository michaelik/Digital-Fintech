package com.example.service.impl;

import com.example.constant.Message;
import com.example.dtos.request.SignInRequestDTO;
import com.example.dtos.request.SignUpDTO;
import com.example.dtos.response.SignInResponseDTO;
import com.example.dtos.response.UserDTO;
import com.example.entity.User;
import com.example.exception.DuplicateResourceException;
import com.example.exception.ResourceNotFoundException;
import com.example.mappers.UserDTOMapper;
import com.example.mappers.UserMapper;
import com.example.repository.UserRepository;
import com.example.security.jwt.JWTUtilService;
import com.example.service.AccountService;
import com.example.service.UserAccessService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final UserAccessService userAccessService;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtilService jwtUtilService;


    @Override
    public void signUp(SignUpDTO request) {
        Optional<User> userEmailExist = userRepository
                .findByEmail(request.email());
        if (userEmailExist.isPresent()) throw new DuplicateResourceException(
                Message.EMAIL_TAKEN
        );
        userRepository.save(UserMapper.to(
                request,
                passwordEncoder,
                accountService
        ));
    }

    @Override
    public SignInResponseDTO signIn(SignInRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User principal = (User) authentication.getPrincipal();
        UserDTO userDTO = userDTOMapper.apply(principal);
        String token = jwtUtilService.issueToken(userDTO.username(), userDTO.roles());
        return new SignInResponseDTO(token, userDTO);
    }

    @Override
    public UserDTO getUserById(long userId) {
        // Check if the authenticated user has access
        long accessId = userAccessService.authenticatedUserId();
        if(userId != accessId){
            throw new ResourceNotFoundException(
                    Message.USER_NOT_FOUND.formatted(userId)
            );
        }
        return userRepository.findById(userId)
                .map(UserMapper::from)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                               Message.USER_NOT_FOUND.formatted(userId)
                        )
                );
    }
}
