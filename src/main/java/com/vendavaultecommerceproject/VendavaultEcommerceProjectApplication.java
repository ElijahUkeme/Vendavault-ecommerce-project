package com.vendavaultecommerceproject;

import com.vendavaultecommerceproject.security.entity.Role;
import com.vendavaultecommerceproject.security.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class VendavaultEcommerceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendavaultEcommerceProjectApplication.class, args);
	}
@Bean
	CommandLineRunner runner(RoleRepository repository){
		return args -> {
			if (repository.findByName("MANAGER").isEmpty()){
				repository.save(Role.builder()
								.name("MANAGER")
						.build());
			}
		};
}
}
