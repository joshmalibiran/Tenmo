package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.xml.crypto.dsig.TransformService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcAccountDao implements AccountDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> listAccountsByUserId(int user_id) {
        List<Account> accounts = new ArrayList<>();
        String sql = "select account_id, user_id, balance from account where user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
        while (results.next())   {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account create(Account account) {
        String sql = "INSERT INTO account (user_id, balance) VALUES (?, ?) RETURNING account_id;";
        Integer newID = jdbcTemplate.queryForObject(sql, Integer.class,
                account.getUser_id(), new BigDecimal(1000));



        return get(newID);
    }

    @Override
    public Account get(int id) {
        String sql = "SELECT account_id, user_id, balance from account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToAccount(results);
        }
        return null;
    }

    public void sendMoney(Transfer transfer)    {
       String sql = "Update account Set balance = ?, user_id = ? where account_id = ?;";
       jdbcTemplate.update(sql, get(transfer.getFrom_account_id()).getBalance().subtract(transfer.getAmt_transferred()),
               transfer.getFrom_user_id(), transfer.getFrom_account_id());
    }

    public void receiveMoney(Transfer transfer) {
        String sql = "Update account Set balance = ?, user_id = ? where account_id = ?;";
        jdbcTemplate.update(sql,get(transfer.getTo_account_id()).getBalance().add(transfer.getAmt_transferred()),
                transfer.getTo_user_id(), transfer.getTo_account_id());
    }

    private Account mapRowToAccount(SqlRowSet rowSet)   {
        Account account = new Account();
        account.setId(rowSet.getInt("account_id"));
        account.setUser_id(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }

}
