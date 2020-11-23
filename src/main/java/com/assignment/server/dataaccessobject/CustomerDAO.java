package com.assignment.server.dataaccessobject;

import com.assignment.server.datatransferobject.CustomerDTO;

import java.util.List;

/**
 * Customer Data Access Object
 */
public interface CustomerDAO {

    void save(CustomerDTO customerDTO);

    List<CustomerDTO> getAll();

    CustomerDTO findByCustomerId(Long customerId);
}
