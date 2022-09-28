package com.hwe.helloworldenterprise.repository.service;

import com.hwe.helloworldenterprise.entity.Employee;

import java.util.List;

public interface IEmployeeService {


    public boolean delete(Long id);

    public Employee save(Employee employee);

    public List<Employee> findAll();

    public Employee findById(Long id);

}
