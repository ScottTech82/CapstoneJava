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
	
	private final String APPROVED = "APPROVED";
	private final String REVIEW = "REVIEW";
	private final String REJECTED = "REJECTED";
	private final String NEW = "NEW";
	
	
	
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
	
	
	@GetMapping("reviews/{userId}")
	public ResponseEntity<Iterable<Request>> getReviews(@PathVariable int userId) {
		var x = reqRepo.findByUserId(userId);
		if(x.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var fred = reqRepo.findByStatusAndUserIdNot(REVIEW, userId);
		return new ResponseEntity<Iterable<Request>>(fred, HttpStatus.OK);
		/*
		Optional<Request> fred = reqRepo.findByUserId(userId);
		var fredUser = fred.get();
		var revRequests = reqRepo.findByStatus(REVIEW);
		var fredStatus = revRequests.get();
		if(fredUser.getId() == userId) {
			return

		*/
		
	}
	
	
	@PostMapping
	public ResponseEntity<Request> postRequest(@RequestBody Request request) {
		if(request.getId() != 0 || request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		request.setStatus(NEW);
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
	
	
	//****Custom PUT Methods****
	
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity putReviewOrApproved(@PathVariable int id, @RequestBody Request request) {
		
		request.setStatus(request.getTotal() <= 50 ? APPROVED : REVIEW);
		/*
		if(request.getTotal() <= 50) {request.setStatus(APPROVED);} 
		else {request.setStatus(REVIEW);}
		*/
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity putApprove (@PathVariable int id, @RequestBody Request request) {
		request.setStatus(APPROVED);
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity putReject (@PathVariable int id, @RequestBody Request request) {
		request.setStatus(REJECTED);
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.OK);
	
	}
	
	//****End****	
	
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
