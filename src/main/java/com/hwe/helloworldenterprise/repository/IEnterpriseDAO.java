package com.hwe.helloworldenterprise.repository;

import com.hwe.helloworldenterprise.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnterpriseDAO extends JpaRepository<Enterprise, Long> {

}
