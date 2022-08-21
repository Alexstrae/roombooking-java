package com.virtualpairprogrammers.roombooking.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("matt").password("{noop}secret").authorities("ROLE_ADMIN");
//	}
//

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().authorizeRequests().mvcMatchers(HttpMethod.OPTIONS, "/api/basicAuth/**")
				.permitAll().mvcMatchers("/api/basicAuth/**").hasAnyRole("ADMIN", "USER").and().httpBasic();

		http.csrf().disable().cors().and().authorizeRequests().mvcMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
				.mvcMatchers(HttpMethod.GET, "/api/bookings/**").permitAll().mvcMatchers(HttpMethod.GET, "/api/**")
				.hasAnyRole("ADMIN", "USER").mvcMatchers("/api/**").hasRole("ADMIN").and()
				.addFilter(new JWTAuthorizationFilter(authenticationManager()));
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user1 = User.withUsername("matt").password("{noop}secret").roles("ADMIN").build();
		UserDetails user2 = User.withUsername("jane").password("{noop}secret").roles("USER").build();
		return new InMemoryUserDetailsManager(List.of(user1, user2));
	}
}
