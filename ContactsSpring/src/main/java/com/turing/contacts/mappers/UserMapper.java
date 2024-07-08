package com.turing.contacts.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.turing.contacts.interfaces.EntityMapperInterface;
import com.turing.contacts.models.User;
import com.turing.contacts.models.DTOs.SignUpDTO;
import com.turing.contacts.models.DTOs.UserDTO;

@Service
public class UserMapper implements EntityMapperInterface <User,UserDTO> {
    
    @Override
    public UserDTO toDTO(User entity) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(entity, UserDTO.class);
        return userDTO;
    }

    @Override
    public User toEntity(UserDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(dto, User.class);
        return user;
    } 

    public User signUpMapper(SignUpDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(dto, User.class);
        return user;
    } 
}
