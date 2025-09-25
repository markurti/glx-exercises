package com.hitachi.mobile.repository;

import com.hitachi.mobile.model.CustomerIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerIdentityRepository extends JpaRepository<CustomerIdentity, String> {
}

