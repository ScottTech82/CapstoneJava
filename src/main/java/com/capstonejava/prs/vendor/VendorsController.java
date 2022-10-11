package com.capstonejava.prs.vendor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.mapping.List;
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
	public ResponseEntity<Vendor> getCreatePo(@PathVariable int vendorId) throws SQLException {
		var fred = vendRepo.findById(vendorId).get();
		var xpo = new Po();
		xpo.setVendor(fred);
		
		var xProduct = prodRepo.findByVendorId(vendorId).get();
			//the products for the passed in vendorId
		var xRequest = reqRepo.findByStatus("APPROVED").get(); 
		
		var xfred = fred.getProduct();  //all products by the Vendor id passed in.
		//var xReqLine = reqlineRepo.;
		//for(var reqline : xfred) {
			//reqlineRepo.
		//}
			//trying to get all request lines with those products by that one vendor id passed in.
			//does this need to be a foreach loop?? foreach product find a request line
		
		// need to get Product Id, Product Name, Product Price, & RL Quantity. 
		// then multiply the Price * Quantity for the Poline LineTotal.
		
		String url = "jdbc:mysql://localhost:3306/capstonejavaprs";
        Connection conn = DriverManager.getConnection(url,"root","Train@MAX");
        Statement stmt = conn.createStatement();
        ResultSet xs;

        xs = stmt.executeQuery("SELECT p.Id, p.Name, p.Price, l.Quantity, p.Price * l.Quantity as LineTotal"
        		+ " FROM vendors v"
        		+ " JOIN products p ON v.Id = p.vendorId"
        		+ " JOIN Requestlines l ON p.Id = l.productId"
        		+ " JOIN Requests r ON l.requestId = r.Id"
        		+ " WHERE r.Status = 'APPROVED';");
		while(xs.next()) {
			int pId = xs.getInt("Id"); 
			String pName = xs.getString("Name");
			int pPrice = xs.getInt("Price");
			int lQuant = xs.getInt("Quantity");
			int lineTotal = xs.getInt("LineTotal");
		}
        conn.close();
        
        var sortedLines = new ArrayList<Integer>();
     //   for(var lines : xs) {
			 
					
		
     //   sortedLines.addAll(lines.getId(), null);
		
		
	//	xpo.setPoline(TBD);
		return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
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
