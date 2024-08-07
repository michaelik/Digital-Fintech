package com.example.service.impl;

import com.example.entity.User;
import com.example.service.UserAccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAccessServiceImpl implements UserAccessService {

    public long authenticatedUserId() {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        // Retrieve the authenticated user's ID
        return user.getId();
    }
}
