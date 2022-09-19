package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TransferDao transferDao;

    @PostMapping(path = "/account")
    public Account createAccount(@RequestBody Account account)    {
        Account returned = null;
        try {
            returned = accountDao.create(account);
        }
        catch (ResponseStatusException e)   {
            e.getMessage();
        }
        return returned;
    }

    @GetMapping(path = "/balance")
    public BigDecimal getBalance (@RequestBody int id){
        return accountDao.get(id).getBalance();
    }

    @GetMapping(path = "/users")
    public List<User> listUsers ()  {
        return userDao.findAll();
    }

    @PostMapping(path = "/transfer")
    public void transfer(@RequestBody Transfer transfer)   {
        transferDao.create(transfer);
    }

    @GetMapping(path = "/transfer")
    public List<Transfer> listTransferByUser(@RequestBody int id)  {
        return transferDao.findTransfersByUserId(id);
    }

    @GetMapping(path = "/transfer/details")
    public Transfer findTransferById(@RequestBody int id)   {
        return transferDao.findTransferByTransferId(id);
    }

}
