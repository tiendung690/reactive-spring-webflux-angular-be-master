package org.edu.kushima.controller;

import java.time.Duration;

import javax.validation.Valid;

import org.edu.kushima.model.Customer;
import org.edu.kushima.repository.ReactiveCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api")
public class CustomerController {
 
	@Autowired
	ReactiveCustomerRepository customerRepository;
 
	@GetMapping("/customers")
	public Flux<Customer> getAllCustomers() {
		System.out.println("Get all Customers...");
 
		return customerRepository.findAll().delayElements(Duration.ofMillis(1000));
	}
 
	@PostMapping("/customers/create")
	public Mono<Customer> createCustomer(@Valid @RequestBody Customer customer) {
		System.out.println("Create Customer: " + customer.getName() + "...");
 
		customer.setActive(false);
		return customerRepository.save(customer);
	}
 
	@PutMapping("/customers/{id}")
	public Mono<ResponseEntity<Customer>> updateCustomer(@PathVariable("id") String id,
			@RequestBody Customer customer) {
		System.out.println("Update Customer with ID = " + id + "...");
 
		return customerRepository.findById(id).flatMap(customerData -> {
			customerData.setName(customer.getName());
			customerData.setAge(customer.getAge());
			customerData.setActive(customer.isActive());
			return customerRepository.save(customerData);
		}).map(updatedcustomer -> new ResponseEntity<>(updatedcustomer, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
 
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id) {
		System.out.println("Delete Customer with ID = " + id + "...");
 
		try {
			customerRepository.deleteById(id).subscribe();
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}
 
		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}
 
	@DeleteMapping("/customers/delete")
	public ResponseEntity<String> deleteAllCustomers() {
		System.out.println("Delete All Customers...");
 
		try {
			customerRepository.deleteAll().subscribe();
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}
 
		return new ResponseEntity<>("All customers have been deleted!", HttpStatus.OK);
	}
 
	@GetMapping("/customers/findbyname")
	public Flux<Customer> findByName(@RequestParam String name) {
 
		return customerRepository.findByName(name).delayElements(Duration.ofMillis(1000));
	}
}