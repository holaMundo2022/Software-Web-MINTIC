package com.hwe.helloworldenterprise.controller;

import com.hwe.helloworldenterprise.entity.Enterprise;
import com.hwe.helloworldenterprise.entity.User;
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
public class EnterpriseController {

    @Autowired
    private IEntepriseService entepriseService;

    @RequestMapping("/enterprise")
    public ModelAndView viewEnterprise(Model model, HttpServletRequest session){

        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("enterprise", new Enterprise());
        return getViewEnterprise(new Enterprise(),-1, "", "new");
    }

    @RequestMapping("/enterprise/{id}")
    public ModelAndView viewEnterprise(@PathVariable("id") Long id, Model model, HttpServletRequest session){

        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("enterprise", new Enterprise());
        return getViewEnterprise(entepriseService.findById(id),-1, "", "new");
    }

    @RequestMapping("/enterprise/list")
    public ModelAndView viewEnterpriseList(Model model, HttpServletRequest session){

        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("enterprises", entepriseService.findAll());
        return new ModelAndView("enterprise-list");
    }

    @PostMapping("/enterprise")
    public ModelAndView createEnteprise(@ModelAttribute("enterprise") Enterprise enterprise, Model model, HttpServletRequest session) {

        User user = (User) session.getSession().getAttribute("user");

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);

        try {
            System.out.println("Long = "+enterprise.getId());
            if (enterprise.getId()>0) {
                Enterprise oldEnterprise = entepriseService.findById(enterprise.getId());
                oldEnterprise.setAddress(enterprise.getAddress());
                oldEnterprise.setName(enterprise.getName());
                oldEnterprise.setPhone(enterprise.getPhone());
                oldEnterprise.setDocument(enterprise.getDocument());
                return getViewEnterprise(entepriseService.save(oldEnterprise), 1, "Empresa actualizada correctamente", "update");
            } else {
                Enterprise newEnterprise = entepriseService.save(enterprise);
                return getViewEnterprise(new Enterprise(), 1, "Empresa registrada correctamente", "new");
            }
        } catch (DataAccessException ex) {
            return getViewEnterprise(enterprise, 0, ex.getMostSpecificCause().getMessage(), "new");
        }
    }

    @PostMapping("/api/enterprise")
    @ResponseBody
    public ResponseEntity<Enterprise> save(@RequestBody Enterprise enterprise) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(entepriseService.save(enterprise));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/api/enterprise")
    @ResponseBody
    public ResponseEntity<List<Enterprise>> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(entepriseService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("/api/enterprise/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            if(entepriseService.findById(id) != null){
                entepriseService.delete(id);
                return ResponseEntity.status(HttpStatus.OK).build();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @GetMapping ("/api/enterprise/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            if(entepriseService.findById(id) != null){

                return ResponseEntity.status(HttpStatus.OK).body(entepriseService.findById(id));
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PatchMapping("/api/enterprise/{id}")
    public ResponseEntity<Enterprise> update(@RequestBody Enterprise enterprise) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(entepriseService.save(enterprise));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping ("/api/enterprise/{id}/movements")
    public ResponseEntity<?> transationByEnterprise(@PathVariable("id") Long id) {
        try {
            if(entepriseService.findById(id) != null){
                return ResponseEntity.status(HttpStatus.OK).body(entepriseService.findById(id).getTransactions());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ModelAndView getViewEnterprise(Enterprise enterprise, int status, String msg, String mode) {
        ModelAndView view = new ModelAndView("enterprise");
        view.addObject("enterprise", enterprise);
        view.addObject("status", status);
        view.addObject("msg", msg);
        view.addObject("mode", mode);
        return view;
    }

}
