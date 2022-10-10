package com.capstonejava.prs.requestline;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RequestlineRepository extends CrudRepository<Requestline, Integer>{

	Iterable<Requestline> findByRequestId(int requestId);
	Optional<Requestline> findByProductId(int vendorId);
}
