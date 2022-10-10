package com.capstonejava.prs.vendor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstonejava.prs.po.Po;
import com.capstonejava.prs.product.ProductRepository;
import com.capstonejava.prs.request.RequestRepository;
import com.capstonejava.prs.requestline.RequestlineRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorsController {

	@Autowired
	private VendorRepository vendRepo;
	@Autowired
	private ProductRepository prodRepo;
	@Autowired
	private RequestlineRepository reqlineRepo;
	@Autowired
	private RequestRepository reqRepo;
	
	
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors() {
		Iterable<Vendor> fred = vendRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(fred, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Vendor> getVendor(@PathVariable int id) {
		Optional<Vendor> fred = vendRepo.findById(id);
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(fred.get(), HttpStatus.OK);
	}
	
	@GetMapping("po/{vendorId}")
	public ResponseEntity<Vendor> getCreatePo(@PathVariable int vendorId) {
		var fred = vendRepo.findById(vendorId).get();
		var xpo = new Po();
		xpo.setVendor(fred);
		
		var xProduct = prodRepo.findByVendorId(vendorId).get();
			//the products for the passed in vendorId
		var xRequest = reqRepo.findByStatus("APPROVED").get(); 
		
		var xfred = fred.getProduct();  //all products by the Vendor id passed in.
		var xReqLine = reqlineRepo.findAllById(xfred);
			//trying to get all request lines with those products by that one vendor id passed in.
			//does this need to be a foreach loop?? foreach product find a request line
		
		
		
		
		// need to get Product Id, Product Name, Product Price, & RL Quantity. 
		// then multiply the Price * Quantity for the Poline LineTotal.
		
		var xPoline = 
		
		xpo.setPoline(TBD);
		
	}
	
	@PostMapping
	public ResponseEntity<Vendor> postVendor(@RequestBody Vendor vendor) {
		if(vendor.getId() != 0 || vendor == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Vendor newVendor = vendRepo.save(vendor);
		return new ResponseEntity<Vendor>(newVendor, HttpStatus.CREATED);
		
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putVendor(@PathVariable int id, @RequestBody Vendor vendor) {
		if(id != vendor.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Vendor> fred = vendRepo.findById(vendor.getId());
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		vendRepo.save(vendor);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteVendor(@PathVariable int id) {
		Optional<Vendor> fred = vendRepo.findById(id);
		if(fred.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var x = fred.get();
		vendRepo.delete(x);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
	
}
