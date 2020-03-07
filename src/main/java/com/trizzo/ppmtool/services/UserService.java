package com.trizzo.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.trizzo.ppmtool.domain.User;
import com.trizzo.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.trizzo.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	public User saveUser(User newUser) {
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setUsername(newUser.getUsername());
			newUser.setConfirmPassword("");
			return userRepository.save(newUser); 
		} catch(Exception e) {
			throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists."); 
		}
	}
}
