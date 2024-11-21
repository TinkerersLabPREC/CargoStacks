package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.dtos.UserDto;

public interface UserService {

    UserDto getUser(String username);

    UserDto createUser(UserDto userDto);

    void deleteUser(String username);

    void changePassword(String username, String oldPassword, String newPassword);

}
