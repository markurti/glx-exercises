package com.hitachi.mobile.repository;

import com.hitachi.mobile.model.SimOffers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SIMOffersRepository extends JpaRepository<SimOffers, Long> {
    Optional<SimOffers> findBySimId(Long simId);
}
