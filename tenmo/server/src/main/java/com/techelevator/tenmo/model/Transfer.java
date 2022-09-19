package com.techelevator.tenmo.model;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class Transfer {
    private int transfer_id;
    private int from_user_id;
    private int to_user_id;
    private int from_account_id;
    private int to_account_id;
    private int status = 1;                          // -1, 0, 1     (Rejected, Pending, Approved)
    private BigDecimal amt_transferred;

    @Autowired
    private JdbcTransferDao jdbcTransferDao;
    @Autowired
    private AccountDao accountDao;

    public Transfer()   {
    }


    public Transfer(int transfer_id, int from_user_id, int to_user_id, int from_account_id, int to_account_id, BigDecimal amt_transferred) {
        if (from_user_id != to_user_id && (accountDao.get(from_account_id).getBalance().compareTo(amt_transferred) >= 0) && amt_transferred.compareTo(BigDecimal.ZERO) > 0) {
        this.transfer_id = transfer_id;
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
        this.from_account_id = from_account_id;
        this.to_account_id = to_account_id;
        this.amt_transferred = amt_transferred;
        this.status = 1;
        }
        }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(int from_user_id) {
        if (from_user_id != this.to_user_id) {
            this.from_user_id = from_user_id;
        }
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(int to_user_id) {
        if (to_user_id != this.from_user_id) {
            this.to_user_id = to_user_id;
        }
    }

    public int getFrom_account_id() {
        return from_account_id;
    }

    public void setFrom_account_id(int from_account_id) {
        this.from_account_id = from_account_id;
    }

    public int getTo_account_id() {
        return to_account_id;
    }

    public void setTo_account_id(int to_account_id) {
        this.to_account_id = to_account_id;
    }

    public BigDecimal getAmt_transferred() {
        return amt_transferred;
    }

    public void setAmt_transferred(BigDecimal amt_transferred) {
        this.amt_transferred = amt_transferred;
    }


}
