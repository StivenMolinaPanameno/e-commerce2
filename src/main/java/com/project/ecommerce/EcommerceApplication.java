package com.project.ecommerce;

import com.project.ecommerce.security.entities.ERole;
import com.project.ecommerce.security.entities.RoleEntity;
import com.project.ecommerce.security.entities.UserEntity;
import com.project.ecommerce.security.repository.UserRepository;
import com.project.ecommerce.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);

	}





}
