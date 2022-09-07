package services;

import Repositorios.EmployeesRepository;
import entities.Employees;
import services.Interfaz.IEmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeesServices implements IEmployeeServices {
    private EmployeesRepository employeesRepository;
    public EmployeesServices(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }
    @Override
    public boolean delete(long Id) {
        try {
            employeesRepository.deleteById(Id);
            return true;
        } catch(Exception ex){
            return false;
        }
    }
    @Override
    public Employees save(Employees employee) {
        return employeesRepository.save(employee);
    }
    @Override
    public  List<Employees> findAll(){
        return employeesRepository.findAll();
    }
    @Override
    public Employees findById(Long Id) {
        return employeesRepository.findById(Id).get();
    }

}
