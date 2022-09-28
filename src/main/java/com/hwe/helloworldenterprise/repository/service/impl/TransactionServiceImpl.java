package com.hwe.helloworldenterprise.repository.service.impl;
import com.hwe.helloworldenterprise.entity.Transaction;
import com.hwe.helloworldenterprise.repository.ITransactionDAO;
import com.hwe.helloworldenterprise.repository.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionDAO transactionDAO;

    @Override
    @Transactional
    public boolean delete(Long id) {
        try {
            transactionDAO.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionDAO.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return transactionDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        return transactionDAO.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findAllByEnterprise(Long transaction_id) {
        return transactionDAO.findAllByEnterprise(transaction_id);
    }

    @Override
    public Integer countTransactionByEnterprise(Long id_enterprise,String type) {
        return transactionDAO.countByAmount(id_enterprise,type);
    }

    @Override
    public Integer countTransactionByAdmin(String type) {
        return transactionDAO.countByAmountForAdmin(type);
    }
}
