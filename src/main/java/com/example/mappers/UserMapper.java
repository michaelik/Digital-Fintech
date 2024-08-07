package com.example.mappers;

import com.example.dtos.request.SignUpDTO;
import com.example.dtos.response.UserDTO;
import com.example.entity.User;
import com.example.enums.Gender;
import com.example.service.AccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public interface UserMapper {
    static User to(
            SignUpDTO request,
            PasswordEncoder passwordEncoder,
            AccountService accountService){
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .age(request.age())
                .gender(Gender.valueOf(request.gender()))
                .password(passwordEncoder.encode(request.password()))
                .bvn(request.bvn())
                .account(accountService.createAccount())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    static UserDTO from(
        User user
    ){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getFirstName()+" "+user.getLastName())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .roles(user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .username(user.getUsername())
                .build();
    }
}
