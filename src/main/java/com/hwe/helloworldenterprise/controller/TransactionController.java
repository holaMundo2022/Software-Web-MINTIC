package com.hwe.helloworldenterprise.controller;


import com.hwe.helloworldenterprise.entity.Transaction;
import com.hwe.helloworldenterprise.entity.User;
import com.hwe.helloworldenterprise.repository.service.IEntepriseService;
import com.hwe.helloworldenterprise.repository.service.ITransactionService;
import com.hwe.helloworldenterprise.repository.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IEntepriseService entepriseService;

    @RequestMapping("/transaction")
    public ModelAndView viewTransactions(Model model, HttpServletRequest session){
        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120*60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("enterprises",entepriseService.findAll());

        return getViewTransaction(new Transaction(),-1,"","new");
    }

    @PostMapping("/transaction")
    public ModelAndView save(@ModelAttribute("transaction") Transaction transaction, Model model, HttpServletRequest session){
        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120*60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        model.addAttribute("enterprises",entepriseService.findAll());

        try{
            if(user.getRol().getId()!=1){
                transaction.setEmployee(user.getEmployee());
            }
            Transaction newTransaction = transactionService.save(transaction);
            return getViewTransaction(new Transaction(),1, "Transacción registrada correctamente","new");
        }catch(Exception ex){
            return getViewTransaction(transaction,0, "Error : "+ex.getMessage(),"new");
        }
    }

    @RequestMapping("/transaction/list")
    public ModelAndView viewTransactionList(Model model, HttpServletRequest session){
        User user = (User) session.getSession().getAttribute("user");
        session.getSession().setMaxInactiveInterval(120 * 60);

        if(user == null){
            return new ModelAndView("redirect:/logout");
        }

        model.addAttribute("user",user);
        if(user.getRol().getId() == 1){
            model.addAttribute("transactions",transactionService.findAll());
        }else{
            model.addAttribute("transactions",transactionService.findAllByEnterprise(user.getEmployee().getEnterprise().getId()));
        }


        return new ModelAndView("transaction-list");
    }

    @PostMapping("/api/transaction")
    public ResponseEntity<Transaction> save(@RequestBody Transaction transaction) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(transactionService.save(transaction));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/transaction")
    public ResponseEntity<List<Transaction>> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("/api/transaction/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            if(transactionService.findById(id) != null){
                transactionService.delete(id);
                return ResponseEntity.status(HttpStatus.OK).build();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PatchMapping("/api/transaction/{id}")
    public ResponseEntity<Transaction> update(@RequestBody Transaction transaction) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(transactionService.save(transaction));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public ModelAndView getViewTransaction(Transaction transaction, int status, String msg, String mode) {
        ModelAndView view = new ModelAndView("transaction");
        view.addObject("transaction", transaction);
        view.addObject("status", status);
        view.addObject("msg", msg);
        view.addObject("mode", mode);
        return view;
    }

}
