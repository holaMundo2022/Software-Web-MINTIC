package com.hwe.helloworldenterprise.repository;

import com.hwe.helloworldenterprise.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITransactionDAO extends JpaRepository <Transaction, Long> {

    @Query("from Transaction  t where t.enterprise.id = :enterprise_id")
    public List<Transaction> findAllByEnterprise(Long enterprise_id);

    @Query("select sum(t.amount) from Transaction  t where t.enterprise.id = :enterprise_id and t.type = :type")
    public Integer countByAmount(Long enterprise_id,String type);

    @Query("select sum(t.amount) from Transaction  t where t.type = :type")
    public Integer countByAmountForAdmin(String type);

}
