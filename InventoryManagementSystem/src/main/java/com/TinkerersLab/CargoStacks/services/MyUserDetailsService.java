package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.dao.user.User;
import com.TinkerersLab.CargoStacks.dao.user.UserPrincipal;
import com.TinkerersLab.CargoStacks.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


    private final UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
