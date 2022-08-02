package com.valueIt.demo.repos;

import com.valueIt.demo.entities.clients.Reclamation;
import com.valueIt.demo.repos.clients.ReclamationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ReclamationRepositoryTest {

    ReclamationRepository underTest;
    Reclamation reclamation;
    @BeforeAll
    void init()
    {
        reclamation=new Reclamation(1L,"reclamation of test");
        underTest.save(reclamation);
    }
    @Test
    void findByClientId() {
        //gi
    }
}