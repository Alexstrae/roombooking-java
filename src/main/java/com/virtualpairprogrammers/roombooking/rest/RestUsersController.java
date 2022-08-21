package com.virtualpairprogrammers.roombooking.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtualpairprogrammers.roombooking.data.UserRepository;
import com.virtualpairprogrammers.roombooking.model.AngularUser;
import com.virtualpairprogrammers.roombooking.model.entities.User;

@RestController
@RequestMapping("/api/users")
public class RestUsersController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public List<AngularUser> getAllUsers() {
		return userRepository.findAll().stream().map(user -> new AngularUser(user)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public AngularUser getUser(@PathVariable("id") long id) {
		return new AngularUser(userRepository.findById(id).get());
	}

	@PostMapping()
	public AngularUser newUser(@RequestBody User user) {
		return new AngularUser(userRepository.save(user));
	}

	@PutMapping()
	public AngularUser updateUser(@RequestBody AngularUser updatedUser) {
		User originalUser = userRepository.findById(updatedUser.getId()).get();
		originalUser.setName(updatedUser.getName());
		return new AngularUser(userRepository.save(originalUser));
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") long id) {
		userRepository.deleteById(id);
	}

	@GetMapping("/resetPassword/{id}")
	public void resetPassword(@PathVariable("id") long id) {
		User user = userRepository.findById(id).get();
		user.setPassword("secret");
		userRepository.save(user);
	}

	@GetMapping("/currentUserRole")
	public Map<String, String> getCurrentUsersRole() {
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		String role = "";
		if (roles.size() > 0) {
			GrantedAuthority ga = roles.iterator().next();
			role = ga.getAuthority().substring(5);
		}
		Map<String, String> results = new HashMap<>();
		results.put("role", role);
		return results;
	}

}
