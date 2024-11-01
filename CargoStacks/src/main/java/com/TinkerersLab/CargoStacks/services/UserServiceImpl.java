package com.TinkerersLab.CargoStacks.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TinkerersLab.CargoStacks.dtos.UserDto;
import com.TinkerersLab.CargoStacks.models.Role;
import com.TinkerersLab.CargoStacks.models.dao.user.User;
import com.TinkerersLab.CargoStacks.repository.RoleRepo;
import com.TinkerersLab.CargoStacks.repository.UserRepo;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepo userRepo;

    RoleRepo roleRepo;

    ModelMapper modelMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUser(String username) {
        User user;
        user = userRepo.findByEmail(username);
        if(user == null){
            return null;
        }
        return entityToDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToEntity(userDto);
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role defaultRole = roleRepo.findByName("ROLE_GUEST").get();
        if(user.getRoles() == null){
            user.setRoles(new ArrayList<>());
        }
        user.getRoles().add(defaultRole);
        userRepo.save(user);
        return entityToDto(user);
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepo.findByEmail(username);
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            user.removeRole(role);
        }
        userRepo.delete(user);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepo.findByEmail(username);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
        } else {
            throw new UnsupportedOperationException("Password does not match");
        }
    }

    public User dtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDto entityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
