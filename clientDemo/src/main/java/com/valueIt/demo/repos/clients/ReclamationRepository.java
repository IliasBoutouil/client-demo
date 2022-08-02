package com.valueIt.demo.repos.clients;

import com.valueIt.demo.entities.clients.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository  extends JpaRepository<Reclamation,Long> {
    Page<Reclamation> findByClientId(long id, Pageable pageable);
}
