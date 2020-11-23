package com.assignment.server.service;

import com.assignment.server.datatransferobject.CustomerDTO;

import java.util.List;

/**
 * Customer service which relates the CustomerDAO and CustomerController
 */
public interface CustomerService extends DefaultService<CustomerDTO> {
    void save(CustomerDTO customerDTO);

    List<CustomerDTO> getAll();

    CustomerDTO findById(Long id);
}
