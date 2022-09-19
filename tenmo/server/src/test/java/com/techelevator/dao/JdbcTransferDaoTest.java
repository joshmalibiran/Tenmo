package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDaoTest extends BaseDaoTests{

   private JdbcTransferDao sut;
   private Transfer transfer = new Transfer();

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
        //transfer 1
        transfer.setFrom_user_id(1001);
        transfer.setTo_user_id(1002);
        transfer.setFrom_account_id(2001);
        transfer.setTo_account_id(2002);
        transfer.setAmt_transferred(new BigDecimal(300));

    }

    @Test
    public void createReturnsTrue() {
        Assert.assertTrue(sut.create(transfer));
    }

    @Test
    public void findTransfersByUserIdReturnsCorrectList() {
        List<Transfer> list = new ArrayList<>();
        list = sut.findTransfersByUserId(1001);
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void findTransferByTransferIdReturnsCorrectly() {
        Transfer transfer = sut.findTransferByTransferId(3001);
        Assert.assertEquals(3001, transfer.getTransfer_id());
    }
}
