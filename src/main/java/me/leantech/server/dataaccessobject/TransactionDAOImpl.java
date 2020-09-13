package me.leantech.server.dataaccessobject;

import com.google.common.collect.Sets;
import me.leantech.server.datatransferobject.TransactionDTO;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Transaction Data Access Implementation
 */
@Repository
public class TransactionDAOImpl implements TransactionDAO {

    private Set<TransactionDTO> data = Sets.newTreeSet((e1, e2) -> e1.getTransactionId() - e2.getTransactionId() > 0 ? 1 : 0);

    @Override
    public void save(TransactionDTO transactionDTO) {
        data.add(transactionDTO);
    }

    @Override
    public List<TransactionDTO> getAll() {
        return data.stream().collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByAccountId(Long accountId) {
        return data.stream()
                .filter(transactionDTO -> transactionDTO.getAccountId().equals(accountId))
                .sorted(Comparator.comparing(TransactionDTO::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO findByTransactionId(Long transactionId) {
        return data.stream()
                .filter(accountDTO -> accountDTO.getTransactionId().equals(transactionId))
                .findAny()
                .orElseGet(() -> new TransactionDTO());
    }
}
