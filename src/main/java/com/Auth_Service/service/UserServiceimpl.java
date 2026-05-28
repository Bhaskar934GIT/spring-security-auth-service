package com.Auth_Service.service;

import com.Auth_Service.dto.UserDto;
import com.Auth_Service.entity.User;
import com.Auth_Service.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements UserService{

    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;


    public UserServiceimpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }




    @Override
    public UserDto addUser(UserDto userDto) {
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser=userRepository.save(user);
        UserDto dto=new UserDto();
        BeanUtils.copyProperties(savedUser,dto);
        return dto;
    }
}
