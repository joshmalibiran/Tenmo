package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface AccountDao {
    List<Account> listAccountsByUserId(int user_id);
    Account create(Account account);
    Account get(int id);

    void receiveMoney(Transfer transfer);

    void sendMoney(Transfer transfer);



}
