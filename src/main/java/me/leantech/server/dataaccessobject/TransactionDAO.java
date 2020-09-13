package me.leantech.server.dataaccessobject;

import me.leantech.server.datatransferobject.TransactionDTO;

import java.util.List;

/**
 * Transaction Data Access Object
 */
public interface TransactionDAO {


    void save(TransactionDTO transactionDTO);

    List<TransactionDTO> getAll();

    List<TransactionDTO> findByAccountId(Long accountId);

    TransactionDTO findByTransactionId(Long transactionId);
}
