package com.valueIt.demo.services.implementations;

import com.valueIt.demo.DTO.ReclamationDTO;
import com.valueIt.demo.DTO.SavingReclamationDTO;
import com.valueIt.demo.entities.clients.Reclamation;
import com.valueIt.demo.exceptions.IllegalPageRequestArgumentsException;
import com.valueIt.demo.repos.clients.ReclamationRepository;
import com.valueIt.demo.services.CustomPageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import  static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ReclamationServiceImpTest {
    @Mock
    ReclamationRepository reclamationRepository;
    @Mock
    BindingResult bindingResult ;
    ReclamationServiceImp underTest;
    @BeforeEach
    void setUp()
    {
        underTest=new ReclamationServiceImp(reclamationRepository);
    }
    @Test
    void getAllReclamations() {
        int size=5;
        int page=1;
        Pageable pageable = PageRequest.of(page,size);
        when(reclamationRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));
        Page<ReclamationDTO> reclamations = underTest.getAllReclamations(page, size);
        assertNotNull(reclamations);
        verify(reclamationRepository).findAll(pageable);
    }
    @Test
    void getAllReclamationsThrowsException() {
        int size=-5;
        int page=1;
        assertThatThrownBy(()->underTest.getAllReclamations(page, size)).isInstanceOf(IllegalPageRequestArgumentsException.class);
    }

    @Test
    void getReclamationById () {
        Reclamation expected = new Reclamation();
        expected.setId(1L);
        when(reclamationRepository.findById(1L)).thenReturn(Optional.of(expected));
        ReclamationDTO result = underTest.getReclamationById(1L);
        assertEquals(result.getId(),expected.getId());
        verify(reclamationRepository).findById(1L);
    }

    @Test
    void getAllReclamationsByClientID() {
        int size=5;
        int page=1;
        long id=1L;
        CustomPageRequest pageRequest = new CustomPageRequest(page,size);
        when(reclamationRepository.findByClientId(id,pageRequest.getPageable())).thenReturn(new PageImpl<>(List.of(new Reclamation())));
        Page<ReclamationDTO> reclamations = underTest.getAllReclamationsByClientID(id,page, size);
        assertNotNull(reclamations);
        verify(reclamationRepository).findByClientId(id,pageRequest.getPageable());
    }

    @Test
    void saveReclamation() {
        SavingReclamationDTO savingReclamationDTO = new SavingReclamationDTO();
        savingReclamationDTO.setId(1L);
        savingReclamationDTO.setDescription("Lorem");
        Reclamation reclamationConverted = SavingReclamationDTO.convertToEntity(savingReclamationDTO);
        when(reclamationRepository.save(reclamationConverted)).thenReturn(reclamationConverted);
        ReclamationDTO resultDTO = underTest.saveReclamation(savingReclamationDTO,bindingResult);
        assertEquals(resultDTO, ReclamationDTO.convertToDTO(reclamationConverted));
        verify(reclamationRepository).save(reclamationConverted);
    }


}