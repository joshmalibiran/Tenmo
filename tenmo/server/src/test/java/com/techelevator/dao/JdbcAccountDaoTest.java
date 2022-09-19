package com.techelevator.dao;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;


import java.math.BigDecimal;
import java.util.List;

public class JdbcAccountDaoTest extends BaseDaoTests {

    private JdbcAccountDao sut;
    private Account account = new Account();
    private Transfer transfer = new Transfer();

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
        account.setId(25);
        account.setBalance(new BigDecimal(500));
        account.setUser_id(1001);
        transfer.setFrom_user_id(1001);
        transfer.setTo_user_id(1002);
        transfer.setFrom_account_id(2001);
        transfer.setTo_account_id(2002);
        transfer.setAmt_transferred(new BigDecimal(300));

    }

    @Test
    public void listAccountsByUserIdReturnsCorrectAccount() {
        List<Account> accountList = sut.listAccountsByUserId(1001);
        Assert.assertEquals(2, accountList.size());
        List<Account> accountListt = sut.listAccountsByUserId(1002);
        Assert.assertEquals(1, accountListt.size());

    }

    @Test
    public void createRunsSwimmingly() {
        Account returnedAccount = sut.create(this.account);
        System.out.println(returnedAccount.getId());
        Assert.assertTrue(new BigDecimal(1000).compareTo(returnedAccount.getBalance()) == 0);
    }

    @Test
    public void getReturnsCorrectAccount() {
        Account returnedAccount = sut.get(2001);
        Assert.assertTrue(new BigDecimal(500).compareTo(returnedAccount.getBalance()) == 0);
    }

    @Test
    public void sendMoneyUpdatesCorrectly() {
        sut.sendMoney(transfer);
        Assert.assertTrue(new BigDecimal(200).compareTo(sut.get(2001).getBalance()) == 0);
    }

    @Test
    public void receiveMoneyUpdatesCorrectly() {
        sut.receiveMoney(transfer);
        Assert.assertTrue(new BigDecimal(1500).compareTo(sut.get(2002).getBalance()) == 0);
    }









}
