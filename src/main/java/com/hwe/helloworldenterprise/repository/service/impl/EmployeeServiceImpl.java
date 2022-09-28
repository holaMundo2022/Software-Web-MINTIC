package com.hwe.helloworldenterprise.repository.service.impl;

import com.hwe.helloworldenterprise.entity.Employee;
import com.hwe.helloworldenterprise.repository.IEmployeeDAO;
import com.hwe.helloworldenterprise.repository.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private IEmployeeDAO employeeDao;

    @Override
    public boolean delete(Long id) {
        try {
            employeeDao.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

       @Override
    public Employee save(Employee employee) {

        return employeeDao.save(employee);
    }

    @Override
    public List<Employee> findAll() {

        return employeeDao.findAll();
    }

    @Override
    public Employee findById(Long id) {

        return employeeDao.findById(id).get();
    }
}
