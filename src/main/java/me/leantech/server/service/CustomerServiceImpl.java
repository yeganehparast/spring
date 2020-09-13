package me.leantech.server.service;

import me.leantech.server.dataaccessobject.CustomerDAO;
import me.leantech.server.datatransferobject.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public void save(CustomerDTO customerDTO) {
        customerDAO.save(customerDTO);
    }

    @Override
    public List<CustomerDTO> getAll() {
        return customerDAO.getAll();
    }

    @Override
    public CustomerDTO findById(Long id) {
        return customerDAO.findByCustomerId(id);
    }
}
