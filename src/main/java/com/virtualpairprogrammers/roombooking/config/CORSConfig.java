package com.virtualpairprogrammers.roombooking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// put in config file
		registry.addMapping("/api/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
		.allowedHeaders("*").allowedOrigins(("http://localhost:4200")).allowCredentials(true);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

}
