package me.leantech.server.service;

import me.leantech.server.datatransferobject.TransactionDTO;

import java.util.List;

/**
 * transaction service which relates the TransactionDAO and TransactionController
 */
public interface TransactionService extends DefaultService<TransactionDTO> {

    void save(TransactionDTO transactionDTO);

    List<TransactionDTO> getAll();

    List<TransactionDTO> findByAccountId(Long accountId);

    TransactionDTO findById(Long id);
}
