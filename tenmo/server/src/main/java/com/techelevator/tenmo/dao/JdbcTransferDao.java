package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate)  {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(Transfer transfer) {
        if (transfer.getFrom_account_id()!= transfer.getTo_user_id() && (accountDao.get(transfer.getFrom_account_id()).getBalance().compareTo(transfer.getAmt_transferred()) >= 0) && transfer.getAmt_transferred().compareTo(BigDecimal.ZERO) > 0) {
            String sql = "INSERT INTO transfer(from_user_id, to_user_id, from_account_id, to_account_id, amt_transferred) VALUES(?, ?, ?, ?, ?)";
            try {
                jdbcTemplate.update(sql, transfer.getFrom_user_id(), transfer.getTo_user_id(), transfer.getFrom_account_id(),
                        transfer.getFrom_account_id(), transfer.getAmt_transferred());
            } catch (DataAccessException e) {
                e.getMessage();
                return false;
            }
            accountDao.sendMoney(transfer);
            accountDao.receiveMoney(transfer);
            return true;
        }
        return false;
    }

    @Override
    public List<Transfer> findTransfersByUserId(int id) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, from_user_id, to_user_id, from_account_id, to_account_id, amt_transferred FROM transfer WHERE (from_user_id = ?) OR (to_user_id = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, id);
        while(results.next())   {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public Transfer findTransferByTransferId(int transfer_id) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, from_user_id, to_user_id, from_account_id, to_account_id, amt_transferred FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transfer_id);

        if(results.next())  {
            transfer = mapRowToTransfer(results);
        }

        return transfer;
    }


    private Transfer mapRowToTransfer(SqlRowSet results)   {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(results.getInt("transfer_id"));
        transfer.setFrom_user_id(results.getInt("from_user_id"));
        transfer.setTo_user_id(results.getInt("to_user_id"));
        transfer.setFrom_account_id(results.getInt("from_account_id"));
        transfer.setTo_account_id(results.getInt("to_account_id"));
        transfer.setAmt_transferred(results.getBigDecimal("amt_transferred"));

        return transfer;
    }
}
