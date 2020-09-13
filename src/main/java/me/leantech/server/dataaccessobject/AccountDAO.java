package me.leantech.server.dataaccessobject;

import me.leantech.server.datatransferobject.AccountDTO;

import java.util.List;

/**
 * Account Data Access Object
 */

public interface AccountDAO {

    void save(AccountDTO accountDTO);

    List<AccountDTO> getAll();

    AccountDTO findByAccountId(Long accountId);
}
