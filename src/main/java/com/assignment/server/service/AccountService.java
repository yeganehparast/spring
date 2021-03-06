package com.assignment.server.service;

import com.assignment.server.datatransferobject.AccountDTO;

import java.util.List;

/**
 * Account service which relates the AccountDAO and AccountController
 */

public interface AccountService extends DefaultService<AccountDTO> {

    void save(AccountDTO accountDTO);

    List<AccountDTO> getAll();

    AccountDTO findById(Long id);
}
