package com.hwe.helloworldenterprise.repository;

import com.hwe.helloworldenterprise.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeDAO extends JpaRepository<Employee, Long> {


}
