package com.example.rom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers-orders")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    // Endpoint to create customer with order
    @PostMapping
    public ResponseEntity<Customer> createCustomerWithOrder(@RequestBody CustomerOrderRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());

        Orders order = new Orders();
        order.setOrderDate(request.getOrderDate());
        order.setAmount(request.getAmount());

        // Use the service to create the customer with the order
        Customer createdCustomer = customerOrderService.createCustomerWithOrder(customer, order);

        return ResponseEntity.ok(createdCustomer);
    }

    // Add other endpoints as necessary (like retrieving, updating, etc.)

}
