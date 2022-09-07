package services.Interfaz;

import entities.Employees;
import java.util.List;

public interface IEmployeeServices {
    public boolean delete(long Id);

    public Employees save(Employees employee);

    public List<Employees> findAll();

    public Employees findById(Long Id);
}
