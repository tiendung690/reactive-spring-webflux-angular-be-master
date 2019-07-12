package org.edu.kushima.repository;

import org.edu.kushima.model.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface ReactiveCustomerRepository extends ReactiveCrudRepository<Customer, String> {
 
	Flux<Customer> findByName(String name);
}