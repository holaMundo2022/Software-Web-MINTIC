package com.hwe.helloworldenterprise.controller;


import com.hwe.helloworldenterprise.entity.User;
import com.hwe.helloworldenterprise.repository.service.IEmployeeService;
import com.hwe.helloworldenterprise.repository.service.IRoleService;
import com.hwe.helloworldenterprise.repository.service.IUserService;
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
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IRoleService roleService;

// CONTROLADORES

    @PostMapping("/api/user")
    public ResponseEntity<User> save(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.save(user));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @RequestMapping("/employee/user/{id}")
    public ModelAndView assignUser(@PathVariable("id") Long id, Model model, HttpServletRequest session){
        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120*60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("employee",employeeService.findById(id));
        model.addAttribute("roles",roleService.findAll());

        if(employeeService.findById(id).getUser() == null){
            return getViewUser(new User(),-1,"","new");
        }else{
            return getViewUser(employeeService.findById(id).getUser(),-1,"","update");
        }
    }

    @PostMapping("/user")
    public ModelAndView save(@ModelAttribute("user") User usuario, Model model, HttpServletRequest session){
        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("employee",usuario.getEmployee());
        model.addAttribute("roles",roleService.findAll());

        try {
            if (usuario.getId()>0) {
                User oldUsuario = userService.findById(usuario.getId());
                oldUsuario.setUserName(usuario.getUserName());
                oldUsuario.setImage(usuario.getImage());
                oldUsuario.setPhone(usuario.getPhone());
                oldUsuario.setRol(usuario.getRol());
                oldUsuario.setPhone(usuario.getPhone());
                oldUsuario.setEmployee(usuario.getEmployee());
                if(!usuario.getPassword().equals("")){
                    oldUsuario.setPassword(usuario.getPassword());
                }
                return getViewUser(userService.save(oldUsuario), 1, "Usuario actualizado correctamente", "update");
            } else {
                User newUsuario = userService.save(usuario);
                return getViewUser(new User(), 1, "Usuario registrado correctamente", "new");
            }
        } catch (DataAccessException ex) {
            return getViewUser(usuario, 0, ex.getMostSpecificCause().getMessage(), "new");
        }
    }

    @GetMapping("/api/user")
    public ResponseEntity<List<User>> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            if (userService.findById(id) != null) {
                userService.delete(id);
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            if (userService.findById(id) != null) {

                return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PatchMapping("/api/user/{id}")
    public ResponseEntity<User> update(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.save(user));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public ModelAndView getViewUser(User user, int status, String msg, String mode) {
        ModelAndView view = new ModelAndView("assign-user");
        view.addObject("usuario", user);
        view.addObject("status", status);
        view.addObject("msg", msg);
        view.addObject("mode", mode);
        return view;
    }


}
