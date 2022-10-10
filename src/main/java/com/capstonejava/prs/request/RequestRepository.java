package com.capstonejava.prs.request;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Integer> {

	Optional<Request> findByStatus(String status);
	Optional<Request> findByUserId(int userId);
	Iterable<Request> findByStatusAndUserIdNot(String status, int userId);
}
