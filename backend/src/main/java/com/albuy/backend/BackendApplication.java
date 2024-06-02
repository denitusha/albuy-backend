package com.albuy.backend;

import com.albuy.backend.persistence.entity.Role;
import com.albuy.backend.persistence.entity.User;
import com.albuy.backend.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	public static void main(String[] args)  {SpringApplication.run(BackendApplication.class, args);}

	@Override
	public void run(String... args) throws Exception {

		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if(adminAccount == null) {
			User user = new User();

			user.setEmail("admin@gmail.com");
			user.setFirst_name("admin");
			user.setSecond_name("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
}
