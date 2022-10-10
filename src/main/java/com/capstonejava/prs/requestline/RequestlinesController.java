package com.capstonejava.prs.requestline;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstonejava.prs.request.Request;
import com.capstonejava.prs.request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requestlines")
public class RequestlinesController {

	@Autowired
	private RequestlineRepository reqlnRepo;
	
	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Requestline>> getRequestlines() {
		Iterable<Requestline> fred = reqlnRepo.findAll();
		return new ResponseEntity<Iterable<Requestline>>(fred, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Requestline> getRequestline(@PathVariable int id) {
		Optional<Requestline> fred = reqlnRepo.findById(id);
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Requestline>(fred.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Requestline> postRequestline(@RequestBody Requestline requestline) {
		if(requestline.getId() != 0 || requestline == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Requestline newReqline = reqlnRepo.save(requestline);
		var x = requestline.getRequest().getId();
		this.recalcRequestTotal(x);
		return new ResponseEntity<Requestline>(newReqline, HttpStatus.CREATED);
		
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequestline(@PathVariable int id, @RequestBody Requestline requestline) {
		if(id != requestline.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Requestline> fred = reqlnRepo.findById(requestline.getId());
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqlnRepo.save(requestline);
		var x = requestline.getRequest().getId();
		this.recalcRequestTotal(x);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequestline(@PathVariable int id) {
		Optional<Requestline> fred = reqlnRepo.findById(id);
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var reqln = fred.get();
		reqlnRepo.delete(reqln);
		var x = reqln.getRequest().getId();
		this.recalcRequestTotal(x);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity recalcRequestTotal(int requestId) {
		Optional<Request> fred = reqRepo.findById(requestId);
		 if(fred.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		var req = fred.get();
		var rTotal = 0;		
		Iterable<Requestline> reqlines = reqlnRepo.findByRequestId(req.getId());
		for(var reqline : reqlines) {
				rTotal += reqline.getQuantity()*reqline.getProduct().getPrice();
		}
		req.setTotal(rTotal);
		reqRepo.save(req);
		return new ResponseEntity<>(HttpStatus.OK);

					
		//this is only going to read the 1 requestline passed in by reqId.  I need to read all, so need a loop.
		//req.setTotal(reqln.getQuantity()*reqln.getProduct().getPrice());
		
	}
	
}
