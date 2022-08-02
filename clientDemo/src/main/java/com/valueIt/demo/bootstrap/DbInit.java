package com.valueIt.demo.bootstrap;

import com.valueIt.demo.entities.clients.AppClient;
import com.valueIt.demo.entities.clients.Reclamation;
import com.valueIt.demo.repos.clients.AppClientRepository;
import com.valueIt.demo.repos.clients.ReclamationRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbInit implements ApplicationListener<ContextRefreshedEvent> {

    AppClientRepository clientRepository;
    ReclamationRepository reclamationRepository;

    public DbInit(AppClientRepository clientRepository, ReclamationRepository reclamationRepository) {
        this.clientRepository = clientRepository;
        this.reclamationRepository = reclamationRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        AppClient client = new AppClient("Ilias Boutouil", "ilias@gmail.com");
        clientRepository.saveAll(List.of(client,new AppClient("Wiliam Salah","salah@gmail.com"),new AppClient("Doe","jhon@gmail.com")));
        reclamationRepository.save(new Reclamation("lorem 521462",client));
    }
}
