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



		/*@Autowired
		PasswordEncoder passwordEncoder;
	@Autowired
	IUserService userRepository;

	@Bean
	CommandLineRunner init()
	{
		return args -> {
			UserEntity user = UserEntity.builder()
					.email("stiven@gmail.com")
					.name("Stiven")
					.enabled(true)
					.username("stivenmolina")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();
			UserEntity user2 = UserEntity.builder()
					.email("molina@gmail.com")
					.name("Ramiro")
					.enabled(true)
					.username("ronaldinho")
					.password(passwordEncoder.encode("12345"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.CUSTOMER.name()))
							.build()))
					.build();

			userRepository.save(user);
			userRepository.save(user2);

		};
	}*/

}
