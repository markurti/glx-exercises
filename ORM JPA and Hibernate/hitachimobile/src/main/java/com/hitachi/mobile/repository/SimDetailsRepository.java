package com.hitachi.mobile.repository;

import com.hitachi.mobile.entity.SimDetails;
import com.hitachi.mobile.exception.SIMDoesNotExistException;
import java.util.List;

public interface SimDetailsRepository {
    List<SimDetails> findAll();
    List<SimDetails> findByStatus(String status);
    SimDetails findBySimAndServiceNumber(Long simNumber, Long serviceNumber) throws SIMDoesNotExistException;
    void updateStatus(Integer simId, String status) throws SIMDoesNotExistException;
}
