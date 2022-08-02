package com.valueIt.demo.repos.clients;

import com.valueIt.demo.entities.clients.AppClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppClientRepository extends JpaRepository<AppClient,Long> {
    Page<AppClient> findByOrderByCreatedAtAsc(Pageable pageable);
    Optional<AppClient> findByEmail(String email);
    //todo : find client by reclamation id
}
