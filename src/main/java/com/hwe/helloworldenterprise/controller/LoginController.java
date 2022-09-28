package com.hwe.helloworldenterprise.controller;

import com.hwe.helloworldenterprise.entity.User;
import com.hwe.helloworldenterprise.repository.service.IEmployeeService;
import com.hwe.helloworldenterprise.repository.service.IEntepriseService;
import com.hwe.helloworldenterprise.repository.service.ITransactionService;
import com.hwe.helloworldenterprise.repository.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IEntepriseService entepriseService;
    @Autowired
    private ITransactionService transactionService;

    @RequestMapping({"","/login"})
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @RequestMapping("/dashboard")
    public ModelAndView dashboard(Model model, HttpServletRequest session){
        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        Integer plus = 0;
        Integer minus = 0;
        if(user.getRol().getId() == 1){
            plus = transactionService.countTransactionByAdmin("Ingreso");
            minus = transactionService.countTransactionByAdmin("Egreso");
        }else{
            plus = transactionService.countTransactionByEnterprise(user.getEmployee().getEnterprise().getId(),"Ingreso");
            minus = transactionService.countTransactionByEnterprise(user.getEmployee().getEnterprise().getId(),"Egreso");
        }

        if(plus == null){
            plus = 0;
        }

        if(minus == null){
            minus = 0;
        }

        model.addAttribute("transactionPlus",plus);
        model.addAttribute("transactionMinus",minus);
        model.addAttribute("transactionTotal",(plus-minus));
        model.addAttribute("user",user);
        model.addAttribute("enterprises",entepriseService.findAll().size());
        model.addAttribute("users",userService.findAll().size());
        model.addAttribute("employees",employeeService.findAll().size());

        return new ModelAndView("dashboard");
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest session){
        session.getSession().setAttribute("user", null);
        return new ModelAndView("redirect:/login");
    }


    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session){
        try{
            User user = userService.findByUsername(username);
            if(user == null){
                model.addAttribute("status","error");
                model.addAttribute("message","Credenciales Incorrectas");
                return new ModelAndView("login");
            }else{
                if(user.getPassword().equals(password)){
                    session.setAttribute("user",user);
                    return new ModelAndView("redirect:/dashboard");
                }else{
                    model.addAttribute("status","error");
                    model.addAttribute("message","Credenciales Incorrectas");
                    return new ModelAndView("login");
                }
            }
        }catch(Exception e){
            model.addAttribute("status","error");
            model.addAttribute("message","Se ha generado un error !!!");
            return new ModelAndView("login");
        }
    }



}
