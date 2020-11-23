package com.assignment.server.dataaccessobject;

import com.assignment.server.datatransferobject.CustomerDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newTreeSet;

/**
 * Customer Data Access Object implementation
 */

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private Set<CustomerDTO> data = newTreeSet((e1, e2) -> e1.getCustomerId() - e2.getCustomerId() > 0 ? 1 : 0);


    @Override
    public void save(CustomerDTO customerDTO) {
        data.add(customerDTO);
    }

    @Override
    public List<CustomerDTO> getAll() {
        return data.stream().collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findByCustomerId(Long customerId) {
        return data.stream()
                .filter(accountDTO -> accountDTO.getCustomerId().equals(customerId))
                .findAny()
                .orElseGet(() -> new CustomerDTO());
    }
}
