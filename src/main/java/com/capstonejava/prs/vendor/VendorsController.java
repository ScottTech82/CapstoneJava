package com.capstonejava.prs.vendor;


import java.sql.SQLException;

import java.util.HashMap;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.capstonejava.prs.po.Po;
import com.capstonejava.prs.poline.Poline;



@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorsController {

	@Autowired
	private VendorRepository vendRepo;

	
	
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
	public Po getCreatePo(@PathVariable int vendorId) throws SQLException {
		var fred = vendRepo.findById(vendorId).get();
		var xpo = new Po();
		xpo.setVendor(fred);
		
		var rs = vendRepo.findByStatusCustom("APPROVED");
			//Error cannot convert from Query to Poline.
			//Repo has Iterable<Poline> for the method handling the query.  Need different type?
        HashMap<Integer, Poline> sortedLines = new HashMap<Integer, Poline>();
	    for(var line : rs) {
        	if(!sortedLines.containsKey(line.getId())) {
        		var poline = new Poline();
        		poline.setProduct(line.getProduct());	
        	    poline.setQuantity(0);
        	    poline.setPrice(line.getPrice()); 
        	    poline.setLineTotal(line.getLineTotal()); 
        	    
        		sortedLines.put(line.getId(), poline);	
        	}
        	sortedLines.get(line.getId()).getQuantity();
        	 //c# used, sortedLines[lines.getId()].getQuantity() += lines.getQuantity();       	
        }
			 
		xpo.setPoline(sortedLines);
		double subTotal = 0;
		for(var linetotal : rs) {
			subTotal += linetotal.getLineTotal();
		}
		xpo.setPototal(subTotal);
      
		return xpo;
      
        
    //******Older code I am not using is below******
		//var xProduct = prodRepo.findByVendorId(vendorId).get();//the products for the passed in vendorId
		//var xRequest = reqRepo.findByStatus("APPROVED").get(); 
		//var xfred = fred.getProduct();  //all products by the Vendor id passed in.

		// need to get Product Id, Product Name, Product Price, & RL Quantity. 
		// then multiply the Price * Quantity for the Poline LineTotal.
        
		/*
		String url = "jdbc:mysql://localhost:3306/capstonejavaprs";
        Connection conn = DriverManager.getConnection(url,"root","Train@MAX");
        Statement stmt = conn.createStatement();
        ResultSet xs;

        xs = stmt.executeQuery("SELECT p.Id, p.Name, p.Price, l.Quantity, p.Price * l.Quantity as LineTotal"
        		+ " FROM vendors v"
        		+ " JOIN products p ON v.Id = p.vendorId"
        		+ " JOIN Requestlines l ON p.Id = l.productId"
        		+ " JOIN Requests r ON l.requestId = r.Id"
        		+ " WHERE r.Status = 'APPROVED'");
        
        var resultlist = new ArrayList<CustomList>();		
        while(xs.next()) {
        	CustomList c = new CustomList();
        	c.setId(xs.getInt(1));
        	c.setName(xs.getString(2));
        	c.setPrice(xs.getInt(3));
        	c.setQuantity(xs.getInt(4));
        	c.setLineTotal(xs.getInt(5));
        	resultlist.add(c);
        }
        conn.close();
		*/
		
		//Collections.sort(sortedLines);
		
        /*	
        while(xs.next()) {
			int pId = xs.getInt("Id"); 
			String pName = xs.getString("Name");
			int pPrice = xs.getInt("Price");
			int lQuant = xs.getInt("Quantity");
			int lineTotal = xs.getInt("LineTotal");
		}*/
		//public List<xs> extractData(ResultSet xs);
        
      
		
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


