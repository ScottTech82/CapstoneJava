package com.capstonejava.prs.vendor;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capstonejava.prs.poline.Poline;

public interface VendorRepository extends CrudRepository<Vendor, Integer> {


	@Query("SELECT p.id, p.name as Product, p.price, l.quantity, p.price * l.quantity as LineTotal"
        		+ " FROM Vendors v"
        		+ " JOIN Products p ON v.id = p.vendor.id"
        		+ " JOIN Requestlines l ON p.id = l.product.id"
        		+ " JOIN Requests r ON l.request.id = r.id"
        		+ " WHERE r.status = ?1")
	Iterable<Poline> findByStatusCustom(String status);


}
