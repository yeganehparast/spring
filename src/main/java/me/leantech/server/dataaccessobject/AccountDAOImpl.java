package me.leantech.server.dataaccessobject;

import me.leantech.server.datatransferobject.AccountDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newTreeSet;

/**
 * Account Data Access Implementation
 */

@Repository
public class AccountDAOImpl implements AccountDAO {

    private Set<AccountDTO> data = newTreeSet((e1, e2) -> e1.getAccountId() - e2.getAccountId() > 0 ? 1 : 0);


    @Override
    public void save(AccountDTO accountDTO) {
        data.add(accountDTO);
    }

    @Override
    public List<AccountDTO> getAll() {
        return data.stream().collect(Collectors.toList());
    }

    @Override
    public AccountDTO findByAccountId(Long accountId) {
        return data.stream()
                .filter(accountDTO -> accountDTO.getAccountId().equals(accountId))
                .findAny()
                .orElseGet(() -> new AccountDTO());
    }
}
