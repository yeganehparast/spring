package me.leantech.server.service;

import me.leantech.server.datatransferobject.CustomerDTO;

import java.util.List;

/**
 * Customer service which relates the CustomerDAO and CustomerController
 */
public interface CustomerService extends DefaultService<CustomerDTO> {
    void save(CustomerDTO customerDTO);

    List<CustomerDTO> getAll();

    CustomerDTO findById(Long id);
}
