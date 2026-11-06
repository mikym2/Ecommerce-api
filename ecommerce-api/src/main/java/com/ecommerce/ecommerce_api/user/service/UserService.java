package com.ecommerce.ecommerce_api.user.service;

import com.ecommerce.ecommerce_api.exception.DuplicateResourceException;
import com.ecommerce.ecommerce_api.user.dto.RegisterRequest;
import com.ecommerce.ecommerce_api.user.entity.Role;
import com.ecommerce.ecommerce_api.user.entity.User;
import com.ecommerce.ecommerce_api.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request){

        //check if the name exists
        if(userRepository.existsByUsername(request.username())){
            throw new DuplicateResourceException("Username already exists");
        }

        //check if email exists
        if(userRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("Email is already registered");
        }

        //hash password
        String encodePassword =
                passwordEncoder.encode(request.password());

        //create the user and save
        User user = new User(
                request.username(),
                request.email(),
                encodePassword,
                Role.CUSTOMER

        );

        return  userRepository.save(user);
    }
}
