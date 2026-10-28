package com.agustinperez.proyectoSecurity.config;

import com.agustinperez.proyectoSecurity.model.ERole;
import com.agustinperez.proyectoSecurity.model.Role;
import com.agustinperez.proyectoSecurity.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByNameRole(ERole.ROLE_USER).isEmpty()){

            Role roleUser = new Role();

            roleUser.setNameRole(ERole.ROLE_USER);

            roleRepository.save(roleUser);
        }

        if (roleRepository.findByNameRole(ERole.ROLE_ADMIN).isEmpty()){

            Role roleAdmin = new Role();

            roleAdmin.setNameRole(ERole.ROLE_ADMIN);

            roleRepository.save(roleAdmin);
        }
    }
}
