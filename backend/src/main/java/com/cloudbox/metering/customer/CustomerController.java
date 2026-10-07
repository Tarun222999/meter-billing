package com.cloudbox.metering.customer;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/v1/customers", produces = "application/json")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(    @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customer= customerService.createCustomer(customerRequest);

        URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(customer.id())
                    .toUri();

        return ResponseEntity.created(location).body(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse>  getCustomer(@PathVariable("id") Long id){
        return ResponseEntity.of(customerService.getCustomer(id));
    } 

}
