package com.valueIt.demo.repos;

import com.valueIt.demo.entities.clients.AppClient;
import com.valueIt.demo.repos.clients.AppClientRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AppClientRepositoryTest {
    @Autowired
    AppClientRepository underTest;
     AppClient appClient=new AppClient(1L,"joe","joe@mail.com");

    @BeforeEach
    void init()
    {
        underTest.save(appClient);
    }

    @Test
    @DisplayName("should return the list of clients")
    void findByOrderByCreatedAtAsc() {
        Page<AppClient> clients = underTest.findByOrderByCreatedAtAsc(PageRequest.of(1,1));
        assertEquals(1,clients.getTotalElements());
    }

    @Test
    @DisplayName("should find client by email")
    void findByEmail() {
        //given
        String email="joe@mail.com";
        //when
        Optional<AppClient> result= underTest.findByEmail(email);
        //then
        assertTrue(result.isPresent());
        assertTrue(result.get().getEmail().equals(appClient.getEmail()));
    }
    @Test
    @DisplayName("should not find client")
    void failToFindByEmail() {
        //given
        String wrongEmail="test@mail.com";
        //when
        Optional<AppClient> result= underTest.findByEmail(wrongEmail);
        //then
        assertFalse(result.isPresent());
    }
}