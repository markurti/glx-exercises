package com.hitachi.mobile.repository.impl;

import com.hitachi.mobile.entity.SimDetails;
import com.hitachi.mobile.exception.SIMDoesNotExistException;
import com.hitachi.mobile.repository.SimDetailsRepository;
import com.hitachi.mobile.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class SimDetailsRepositoryImpl implements SimDetailsRepository {

    @Override
    public List<SimDetails> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM SimDetails", SimDetails.class).getResultList();
        }
    }

    @Override
    public List<SimDetails> findByStatus(String status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM SimDetails", SimDetails.class)
                    .getResultList()
                    .stream()
                    .filter(sim -> status.equalsIgnoreCase(sim.getStatus()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public SimDetails findBySimAndServiceNumber(Long simNumber, Long serviceNumber) throws SIMDoesNotExistException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<SimDetails> simDetails = session.createQuery(
                            "FROM SimDetails WHERE simNumber = :simNumber AND serviceNumber = :serviceNumber",
                            SimDetails.class)
                    .setParameter("simNumber", simNumber)
                    .setParameter("serviceNumber", serviceNumber)
                    .getResultList();

            if (simDetails.isEmpty()) {
                throw new SIMDoesNotExistException("No SIM details found for given service and SIM number");
            }

            return simDetails.get(0);
        }
    }

    @Override
    public void updateStatus(Integer simId, String status) throws SIMDoesNotExistException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            SimDetails simDetails = session.get(SimDetails.class, simId);
            if (simDetails == null) {
                throw new SIMDoesNotExistException("No SIM details found for given Id");
            }

            simDetails.setStatus(status);
            session.merge(simDetails);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
