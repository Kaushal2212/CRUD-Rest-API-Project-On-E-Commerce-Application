package com.example.rom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    // Method to create a customer with an order
    public Customer createCustomerWithOrder(Customer customer, Orders order) {
        // Set the customer to the order
        order.setCustomer(customer);

        // Save the order first
        Orders savedOrder = ordersRepository.save(order);

        // Set the saved order to the customer
        customer.setOrder(savedOrder);

        // Save the customer
        return customerRepository.save(customer);
    }

    // Other service methods for managing customers and orders can go here
}
