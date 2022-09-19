package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    boolean create(Transfer transfer);
    List<Transfer> findTransfersByUserId(int from_user_id);
    Transfer findTransferByTransferId(int transfer_id);

}
