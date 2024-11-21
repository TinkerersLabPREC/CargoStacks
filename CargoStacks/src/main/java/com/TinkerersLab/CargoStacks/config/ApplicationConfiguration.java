package com.TinkerersLab.CargoStacks.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.TinkerersLab.CargoStacks.dtos.UserDto;
import com.TinkerersLab.CargoStacks.models.Role;
import com.TinkerersLab.CargoStacks.models.dao.user.User;
import com.TinkerersLab.CargoStacks.services.RoleServiceImpl;
import com.TinkerersLab.CargoStacks.services.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class ApplicationConfiguration {

    @Autowired
    ApplicationProperties applicationProperties;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserServiceImpl userService, RoleServiceImpl roleService) {
        return args -> {

            // Create default roles if not present
            Role adminRole = roleService.getRoleOrNull("ROLE_" + ApplicationConstants.ROLE_ADMIN);
            Role guestRole = roleService.getRoleOrNull("ROLE_" + ApplicationConstants.ROLE_GUEST);

            if (adminRole == null) {
                adminRole = roleService.createRole("ROLE_" + ApplicationConstants.ROLE_ADMIN);
            }
            if (guestRole == null) {
                guestRole = roleService.createRole("ROLE_" + ApplicationConstants.ROLE_GUEST);
            }

            // Create default admin user if not present
            UserDto user = userService.getUser(applicationProperties.getAdminEmail());
            if (user == null) {
                UserDto admin = new UserDto();
                admin.setEmail(applicationProperties.getAdminEmail());
                admin.setPassword(applicationProperties.getAdminPassword());
                admin.setOrganization("Prec");
                admin.setAddress("Loni");
                admin.setDepartment("Tinkerers lab");
                admin.setUserDescription("A default admin account for testing");
                admin.setContact("+91258963214");
                admin.setUserDescription("Default admin user");
                User userEntity = userService.dtoToEntity(admin);
                userEntity.assignRole(adminRole);
                userService.createUser(userService.entityToDto(userEntity));
            }
        };
    }
}
