package com.capstonejava.prs.request;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestsController {

	
	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Request>> getRequests() {
		Iterable<Request> fred = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(fred, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Request> getRequest(@PathVariable int id) {
		Optional<Request> fred = reqRepo.findById(id);
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(fred.get(), HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request) {
		if(request.getId() != 0 || request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Request newReq = reqRepo.save(request);
		return new ResponseEntity<Request>(newReq, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequest(@PathVariable int id, @RequestBody Request request) {
		if(id != request.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Request> fred = reqRepo.findById(request.getId());
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequest(@PathVariable int id) {
		Optional<Request> fred = reqRepo.findById(id);
		if (fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var x = fred.get();
		reqRepo.delete(x);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
