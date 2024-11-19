package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.models.dao.user.User;
import com.TinkerersLab.CargoStacks.models.dao.user.UserPrincipal;
import com.TinkerersLab.CargoStacks.repository.UserRepo;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class MyUserDetailsService implements UserDetailsService {


    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
