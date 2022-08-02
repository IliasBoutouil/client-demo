package com.valueIt.demo.services.implementations;

import com.valueIt.demo.DTO.AppClientDTO;
import com.valueIt.demo.entities.clients.AppClient;
import com.valueIt.demo.exceptions.IllegalPageRequestArgumentsException;
import com.valueIt.demo.exceptions.UnableToSaveEntityException;
import com.valueIt.demo.repos.clients.AppClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyLong;
import static  org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AppClientServiceImpTest {
    @Mock
    BindingResult bindingResult ;
    @Mock
    private AppClientRepository appClientRepository;
    private AppClientServiceImp underTest;
     @BeforeEach
     void setUp()
     {
         underTest=new AppClientServiceImp(appClientRepository);
     }
    AppClient dummyClient = new AppClient(1L,"joe","joe@mail.com");
     AppClientDTO dummyClientDTO= AppClientDTO.convertToDTO(dummyClient);

    @Nested @DisplayName("Fetching clients should :")
    class fetchingClients
    {
        @Test @DisplayName("get all clients")
        void shouldGetAllClients() {
            //given
            int page = 5;
            int size=10;
            PageRequest pageRequest =PageRequest.of(page,size);
            //when
            when(appClientRepository.findByOrderByCreatedAtAsc(pageRequest)).thenReturn(new PageImpl<>(new ArrayList<>()));
            underTest.getAllClients(page,size);
            //then
            verify(appClientRepository).findByOrderByCreatedAtAsc(pageRequest);
        }
        @Test @DisplayName("throw exception due to page number or size")
        void getAllClientsThrowsException() {
            //given
            int page = -5;
            int size=10;
            //then
            assertThatThrownBy(()->underTest.getAllClients(page,size)).isInstanceOf(IllegalPageRequestArgumentsException.class);
        }

        @Test @DisplayName("get one client using id")
        void shouldGetClientById() {
            long id=1L;
            //when
            when(appClientRepository.findById(id)).thenReturn(Optional.of(dummyClient));
            AppClientDTO result = underTest.getClientById(id);
            //then
            verify(appClientRepository).findById(id);
            assertEquals(result,AppClientDTO.convertToDTO(dummyClient));
        }
        @Test @DisplayName("throw exception due to id not found")
        void shouldNotGetClientById() {
            //when
            when(appClientRepository.findById(anyLong())).thenReturn(null);
            //then
            assertThatThrownBy(()->underTest.getClientById(anyLong())).isInstanceOf(RuntimeException.class);
            verify(appClientRepository).findById(anyLong());
        }

        @Test @DisplayName("get one client using email")
        void getClientByEmail() {
            //when
            when(appClientRepository.findByEmail(dummyClient.getEmail())).thenReturn(Optional.of(dummyClient));
            AppClientDTO result = underTest.getClientByEmail(dummyClient.getEmail());
            //then
            verify(appClientRepository).findByEmail(dummyClient.getEmail());
            assertEquals(result,AppClientDTO.convertToDTO(dummyClient));
        }
        @Test @DisplayName("throw exception due to email not found")
        void shouldNotGetClientByEmail() {
            //when
            when(appClientRepository.findByEmail(anyString())).thenReturn(null);
            //then
            assertThatThrownBy(()->underTest.getClientByEmail(anyString())).isInstanceOf(RuntimeException.class);
        }
    }

    @Nested @DisplayName("Adding clients should :")
    class savingClients
    {
        @DisplayName("save client")
        @Test
        void shouldSaveClient() {
            when(appClientRepository.save(dummyClient)).thenReturn(dummyClient);
            AppClientDTO clientDTO = underTest.saveClient(dummyClientDTO,bindingResult);
            assertEquals(clientDTO, AppClientDTO.convertToDTO(dummyClient));
            verify(appClientRepository).save(dummyClient);
        }

        @Test
        @DisplayName("throw exception, fields are null")
        void shouldNotSaveClient() {
            //given
            AppClient toSave = new AppClient(1L, null, null);
            //when
            when(appClientRepository.save(toSave)).thenReturn(toSave);
            //then
            assertThatThrownBy(() -> underTest.saveClient(dummyClientDTO,bindingResult)).isInstanceOf(UnableToSaveEntityException.class);
            verify(appClientRepository).save(dummyClient);
        }
        @Test
        @DisplayName("throw exception, email already used")
        void shouldNotSaveExistingEmail() {
            //when
            when(appClientRepository.findByEmail("joe@mail.com")).thenReturn(Optional.of(dummyClient));
            //then
            assertThatThrownBy(() -> underTest.saveClient(dummyClientDTO,bindingResult)).isInstanceOf(UnableToSaveEntityException.class).hasMessage("the email "+dummyClient.getEmail()+" is already used, please try another one");
        }
    }

    @Nested @DisplayName("Updating clients should :")
    class updatingClients
    {
        @Test
        void shouldUpdateClient() {
         //given
            AppClientDTO clientToUpdateDTO = new AppClientDTO(1L, "new name", "new-email@gmail.com");
            AppClient convertedClient = AppClientDTO.convertToEntity(clientToUpdateDTO);
            //when
            when(appClientRepository.findByEmail(clientToUpdateDTO.getEmail())).thenReturn(Optional.empty());
            when(appClientRepository.save(convertedClient)).thenReturn(convertedClient);
             underTest.updateClient(clientToUpdateDTO,bindingResult);
            verify(appClientRepository).findByEmail(clientToUpdateDTO.getEmail());
            verify(appClientRepository).save(convertedClient);
        }
        @Test
        void shouldNotUpdateNullFields() {
            //given
            AppClient clientToUpdate = new AppClient( );
            clientToUpdate.setEmail("new-email@gmail.com");
            //when
            assertThatThrownBy(()->underTest.updateClient(AppClientDTO.convertToDTO(clientToUpdate),bindingResult)).isInstanceOf(UnableToSaveEntityException.class);
            //then
            verify(appClientRepository).findByEmail(clientToUpdate.getEmail());
            verify(appClientRepository).save(clientToUpdate);
        }
    }
    @Test
    void deleteClient() {
         doNothing().when(appClientRepository).deleteById(anyLong());
        underTest.deleteClient(anyLong());
        verify(appClientRepository).deleteById(anyLong());
    }
    @Test
    void deleteClientThrowsException() {
        doThrow(RuntimeException.class).doNothing().when(appClientRepository).deleteById(anyLong());
        assertThatThrownBy(()->underTest.deleteClient(anyLong())).isInstanceOf(RuntimeException.class) ;
        verify(appClientRepository).deleteById(anyLong());
    }
}