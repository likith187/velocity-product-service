package com.example.product;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {
	
	private final Environment env;
	
	@Bean
	public OpenAPI customOpenAPI() {
		Server server = new Server();
		server.setUrl(env.getProperty("springdoc.server.domain"));

		return new OpenAPI().servers(List.of(server)).info(new Info().title("Product APIs (Velocity)")
				.description("Velocity Product APIs that allow reading, inserting, updating and deleting products"));
	}
	
}
