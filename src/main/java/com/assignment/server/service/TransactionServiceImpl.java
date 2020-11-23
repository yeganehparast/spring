package com.assignment.server.service;

import com.assignment.server.dataaccessobject.TransactionDAO;
import com.assignment.server.datatransferobject.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionDAO transactionDAO;

    @Autowired
    public TransactionServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public void save(TransactionDTO transactionDTO) {
        transactionDAO.save(transactionDTO);
    }

    @Override
    public List<TransactionDTO> getAll() {
        return transactionDAO.getAll();
    }

    @Override
    public List<TransactionDTO> findByAccountId(Long accountId) {
        return transactionDAO.findByAccountId(accountId);
    }

    @Override
    public TransactionDTO findById(Long id) {
        return transactionDAO.findByTransactionId(id);
    }
}
