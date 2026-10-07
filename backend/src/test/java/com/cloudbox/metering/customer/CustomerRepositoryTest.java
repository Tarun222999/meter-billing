package com.cloudbox.metering.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomerRepositoryTest {

    @Container 
    @ServiceConnection 
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"));


    @Autowired 
    private CustomerRepository customerRepository;

    @Autowired 
    private TestEntityManager  entityManager;

    @Test 
    void shouldSaveAndFindCustomer(){
        Customer customer=new Customer("rutu","Rutu@gmail.com");
        Customer savedUser = customerRepository.save(customer);
        entityManager.flush();
        entityManager.clear();
        Optional<Customer> foundUser = customerRepository.findById(savedUser.getId());

        //unwrap
        Customer found = foundUser.orElseThrow();

       
        assertNotNull(found.getCreatedAt());
        assertEquals(savedUser.getEmail(), found.getEmail());

        
        assertThrows(DataIntegrityViolationException.class, 
            ()->customerRepository.saveAndFlush(new Customer("rutu","rutu@gmail.com")));


    }
    
}
