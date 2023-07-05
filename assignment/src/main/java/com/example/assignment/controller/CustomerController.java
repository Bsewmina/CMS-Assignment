package com.example.assignment.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.model.Customer;
import com.example.assignment.repository.CustomerRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer")
    public List<Customer> getAllEmployees() {
        return customerRepository.findAll();
        
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity < Customer > getCustomerById(@PathVariable(value = "id") Long customerId)
    throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + customerId));
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("/customer")
    Customer newCustomer(@RequestBody Customer customer) {
    return customerRepository.save(customer);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity < Customer > updateCustomer(@PathVariable(value = "id") Long customerID,
        @Valid @RequestBody Customer newcustomer) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerID)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + customerID));

        customer.setName(newcustomer.getName());
        customer.setDOB(newcustomer.getDOB());
        customer.setNIC(newcustomer.getNIC());
        customer.setMobile(newcustomer.getMobile());
        customer.setAddress(newcustomer.getAddress());
        final Customer updatedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    // @DeleteMapping("/customer/{id}")
    // public Map < String, Boolean > deleteEmployee(@PathVariable(value = "id") Long employeeId)
    // throws ResourceNotFoundException {
    //     Employee employee = employeeRepository.findById(employeeId)
    //         .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

    //     employeeRepository.delete(employee);
    //     Map < String, Boolean > response = new HashMap < > ();
    //     response.put("deleted", Boolean.TRUE);
    //     return response;
    // }
    
}
