package com.example.rom;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create a new Customer
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    // Get all Customers
//    @GetMapping
//    public List<Customer> getAllCustomers() {
//        return customerService.getAllCustomers();
//    }

    // Get a Customer by ID
    @GetMapping("/{customerid}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerid) {
        Optional<Customer> customer = customerService.getCustomerById(customerid);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a Customer by ID
    @PutMapping("/{customerid}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerid, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(customerid, customerDetails);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Customer by ID
    @DeleteMapping("/{customerid}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerid) {
        customerService.deleteCustomer(customerid);
        return ResponseEntity.noContent().build();
    }
    
    private static final List<String> VALID_SORT_FIELDS = Arrays.asList("firstName", "email");

    @GetMapping("/customers")
    public Page<Customer> getCustomers(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(required = false) String sortBy) {

        Pageable pageable;

        // If sorting is provided and valid
        if (sortBy != null && !sortBy.isEmpty() && VALID_SORT_FIELDS.contains(sortBy)) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        } else {
            // Default to sorting by firstName if no valid sortBy is provided
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName"));
        }

        return customerService.getCustomers(pageable);
    }
    
    
    @GetMapping("/customers/{id}")
    public UserWithNoPassword getCustomer(@PathVariable Long id) {
        return customerService.getCustomerWithoutPassword(id);
    }

//    @GetMapping("/customers")
//    public List<UserWithNoPassword> getAllCustomers() {
//        return customerService.getAllCustomersWithoutPassword();
//    }
}

