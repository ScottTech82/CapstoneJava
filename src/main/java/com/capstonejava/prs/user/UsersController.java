package com.capstonejava.prs.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UsersController {

	
	@Autowired
	private UserRepository userRepo;
	
	
	
	@GetMapping
	public ResponseEntity<Iterable<User>> getUsers() {
		Iterable<User> fred = userRepo.findAll();
		return new ResponseEntity<Iterable<User>>(fred, HttpStatus.OK);
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		Optional<User> fred = userRepo.findById(id);
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(fred.get(), HttpStatus.OK);
	}
	
	@GetMapping("{username}/{password}")
	public ResponseEntity<User> getLogin(@PathVariable String username, @PathVariable String password) {
		Optional<User> xname = userRepo.findByUsername(username);
		if(xname.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<User> xpass = userRepo.findByPassword(password);
		if(xpass.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(xname.get(), HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<User> postUser(@RequestBody User user) {
		if(user.getId() != 0 || user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User newUser = userRepo.save(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putUser(@PathVariable int id, @RequestBody User user) {
		if(id != user.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<User> fred = userRepo.findById(user.getId());
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.save(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable int id) {
		Optional<User> fred = userRepo.findById(id);
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var x = fred.get();
		userRepo.delete(x);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
}
