package com.example.rom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrdersRepository orderRepository;

    @Transactional
    public Customer createCustomer(Customer customer) {
        // Save the associated order if it is new
        Orders order = customer.getOrder();
        if (order != null && order.getOrderId() == null) {
            orderRepository.save(order);  // Save the order first if it is not already saved
        }
        
        // Save the customer, which will also save the associated order because of the cascade
        return customerRepository.save(customer);
    }

    // Get all Customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get Customer by ID
    public Optional<Customer> getCustomerById(Long customerid) {
        return customerRepository.findById(customerid);
    }

    // Update Customer
    public Customer updateCustomer(Long customerid, Customer customerDetails) {
        Optional<Customer> customer = customerRepository.findById(customerid);
        if (customer.isPresent()) {
            Customer existingCustomer = customer.get();
            existingCustomer.setFirstName(customerDetails.getFirstName());
            existingCustomer.setLastName(customerDetails.getLastName());
            existingCustomer.setEmail(customerDetails.getEmail());
            existingCustomer.setAddress(customerDetails.getAddress());
            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    // Delete Customer
    public void deleteCustomer(Long customerid) {
        customerRepository.deleteById(customerid);
    }
    
    public Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
    
    
    public UserWithNoPassword getCustomerWithoutPassword(Long customerId) {
        return customerRepository.findUserWithNoPassword(customerId);
    }

    public List<UserWithNoPassword> getAllCustomersWithoutPassword() {
        return customerRepository.findAllUsersWithNoPassword();
    }
}

