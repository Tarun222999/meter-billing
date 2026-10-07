package com.cloudbox.metering.customer;

import java.util.Optional;

import org.springframework.stereotype.Service;


@Service 
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {

        Customer customer = new Customer(customerRequest.name(), customerRequest.email());

        Customer savedCustomer = customerRepository.save(customer);

        return toResponse(savedCustomer);

    }

    public Optional<CustomerResponse> getCustomer(Long id){
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        
        return foundCustomer.map(CustomerService::toResponse);
    }

    private static CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getCreatedAt());
    }
}
