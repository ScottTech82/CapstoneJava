package com.capstonejava.prs.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductsController {

	@Autowired
	private ProductRepository prodRepo;
	
	
	@GetMapping
	public ResponseEntity<Iterable<Product>> getProducts() {
		Iterable<Product> fred = prodRepo.findAll();
		return new ResponseEntity<Iterable<Product>>(fred, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		Optional<Product> fred = prodRepo.findById(id);
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(fred.get(), HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<Product> postProduct(@RequestBody Product product) {
		if(product.getId() != 0 || product == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Product newProd = prodRepo.save(product);
		return new ResponseEntity<Product>(newProd, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putProduct(@PathVariable int id, @RequestBody Product product) {
		if(id != product.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Product> fred = prodRepo.findById(product.getId());
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		prodRepo.save(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		Optional<Product> fred = prodRepo.findById(id);
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var x = fred.get();
		prodRepo.delete(x);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
}
