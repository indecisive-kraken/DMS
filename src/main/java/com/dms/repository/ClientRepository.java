package com.dms.repository;


import com.dms.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends
        JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

        Optional<Client> findByCompanyName(String CompanyName);
        Optional<Client> findByCid(String cid);

//    @Query(value = "SELECT * from clients", nativeQuery = true)
//    List<Client> clientList();

//    Page<Client> findClients(Pageable pageable);

}
