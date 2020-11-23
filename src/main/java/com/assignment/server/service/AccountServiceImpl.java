package com.assignment.server.service;

import com.assignment.server.dataaccessobject.AccountDAO;
import com.assignment.server.datatransferobject.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountDAO accountDAO;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void save(AccountDTO accountDTO) {
        accountDAO.save(accountDTO);
    }

    public List<AccountDTO> getAll() {
        return accountDAO.getAll();
    }

    public AccountDTO findById(Long id) {
        return accountDAO.findByAccountId(id);
    }
}
