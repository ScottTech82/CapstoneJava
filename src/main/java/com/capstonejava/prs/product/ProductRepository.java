package com.capstonejava.prs.product;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	Optional<Product> findByVendorId(int vendorId);

}
