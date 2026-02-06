package com.dms.repository;

import com.dms.model.auth.Capability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapabilityRepository extends JpaRepository<Capability, Long> {

    @Query(value = "SELECT name FROM capabilities", nativeQuery = true)
    List<String> capabilityList();


}
