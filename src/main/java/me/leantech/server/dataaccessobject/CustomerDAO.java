package me.leantech.server.dataaccessobject;

import me.leantech.server.datatransferobject.CustomerDTO;

import java.util.List;

/**
 * Customer Data Access Object
 */
public interface CustomerDAO {

    void save(CustomerDTO customerDTO);

    List<CustomerDTO> getAll();

    CustomerDTO findByCustomerId(Long customerId);
}
