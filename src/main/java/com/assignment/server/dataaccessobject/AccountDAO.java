package com.assignment.server.dataaccessobject;

import com.assignment.server.datatransferobject.AccountDTO;

import java.util.List;

/**
 * Account Data Access Object
 */

public interface AccountDAO {

    void save(AccountDTO accountDTO);

    List<AccountDTO> getAll();

    AccountDTO findByAccountId(Long accountId);
}
