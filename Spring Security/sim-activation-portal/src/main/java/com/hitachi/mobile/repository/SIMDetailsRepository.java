package com.hitachi.mobile.repository;

import com.hitachi.mobile.model.SimDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SIMDetailsRepository extends JpaRepository<SimDetails, Long> {
    Optional<SimDetails> findBySimNumberAndServiceNumber(String simNumber, String serviceNumber);
}