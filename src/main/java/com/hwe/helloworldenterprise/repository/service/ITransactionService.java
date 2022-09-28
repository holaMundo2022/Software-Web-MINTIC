package com.hwe.helloworldenterprise.repository.service;


import com.hwe.helloworldenterprise.entity.Transaction;

import java.util.List;

public interface ITransactionService  {

    //crud
    public boolean delete(Long id);

    public Transaction save(Transaction transaction);

    public List<Transaction> findAll();

    public Transaction findById(Long id);

    public List<Transaction> findAllByEnterprise(Long transaction_id);

    public Integer countTransactionByEnterprise(Long id_enterprise,String type);
    public Integer countTransactionByAdmin(String type);


}
