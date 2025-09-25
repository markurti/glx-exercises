package com.hitachi.mobile.service;

import com.hitachi.mobile.entity.SimDetails;
import com.hitachi.mobile.exception.SIMDoesNotExistException;
import com.hitachi.mobile.repository.SimDetailsRepository;
import com.hitachi.mobile.repository.impl.SimDetailsRepositoryImpl;

import java.util.List;

public class SimDetailsService {
    private final SimDetailsRepository simDetailsRepository;

    public SimDetailsService() {
        this.simDetailsRepository = new SimDetailsRepositoryImpl();
    }

    public List<SimDetails> getActiveSimDetails() {
        return simDetailsRepository.findByStatus("active");
    }

    public String getSimStatus(Long simNumber, Long serviceNumber) throws SIMDoesNotExistException {
        SimDetails simDetails = simDetailsRepository.findBySimAndServiceNumber(simNumber, serviceNumber);
        return simDetails.getStatus();
    }

    public void activateSim(Integer simId) throws SIMDoesNotExistException {
        simDetailsRepository.updateStatus(simId, "active");
    }
}
