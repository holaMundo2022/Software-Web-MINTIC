package com.hwe.helloworldenterprise.controller;

import com.hwe.helloworldenterprise.entity.Employee;
import com.hwe.helloworldenterprise.entity.User;
import com.hwe.helloworldenterprise.repository.service.IEmployeeService;
import com.hwe.helloworldenterprise.repository.service.IEntepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IEntepriseService entepriseService;

    @RequestMapping("/employee")
    public ModelAndView viewEmployee(Model model, HttpServletRequest session){
        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("enterprises",entepriseService.findAll());

        return getViewEmployee(new Employee(),-1,"","new");
    }

    @PostMapping("/employee")
    public ModelAndView employee(@ModelAttribute("employee") Employee employee, Model model,HttpServletRequest session){
        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("enterprises",entepriseService.findAll());

        try {
            if (employee.getId()>0) {
                Employee oldEmployee = employeeService.findById(employee.getId());
                oldEmployee.setEmail(employee.getEmail());
                oldEmployee.setName(employee.getName());
                oldEmployee.setLastname(employee.getLastname());
                return getViewEmployee(employeeService.save(employee), 1, "Empleado actualizado correctamente", "update");
            } else {
                Employee newEmployee = employeeService.save(employee);
                return getViewEmployee(new Employee(), 1, "Empleado registrado correctamente", "new");
            }
        } catch (DataAccessException ex) {
            return getViewEmployee(employee, 0, ex.getMostSpecificCause().getMessage(), "new");
        }

    }

    @RequestMapping("/employee/{id}")
    public ModelAndView viewEmployee(@PathVariable("id") Long id, Model model, HttpServletRequest session){

        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("enterprises",entepriseService.findAll());

        return getViewEmployee(employeeService.findById(id),-1, "", "update");
    }

    @RequestMapping("/employee/list")
    public ModelAndView viewEmployeeList(Model model, HttpServletRequest session){
        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("employees",employeeService.findAll());

        return new ModelAndView("employee-list");
    }


    @PostMapping("/api/employee")
    @ResponseBody
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.save(employee));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/api/employee")
    @ResponseBody
    public ResponseEntity<List<Employee>> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/employee/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            if(employeeService.findById(id) != null){
                employeeService.delete(id);
                return ResponseEntity.status(HttpStatus.OK).build();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping ("/api/employee/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            if(employeeService.findById(id) != null){
                return ResponseEntity.status(HttpStatus.OK).body(employeeService.findById(id));
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ModelAndView getViewEmployee(Employee employee, int status, String msg, String mode) {
        ModelAndView view = new ModelAndView("employee");
        view.addObject("employee", employee);
        view.addObject("status", status);
        view.addObject("msg", msg);
        view.addObject("mode", mode);
        return view;
    }

}
