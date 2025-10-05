package com.example.backendapp;

//import com.example.backendapp.Client;
//import com.example.backendapp.FileRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    private static final String CLIENTS_FILE = "clients.json";
    private final FileRepository<Client> repository;
    private List<Client> clients;

    public ClientService(FileRepository<Client> repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() throws IOException {
        this.clients = new ArrayList<>(repository.readAll(CLIENTS_FILE, new TypeReference<>() {}));
    }

    public List<Client> getAllClients() {
        return clients;
    }

    public Optional<Client> getClientById(String id) {
        return clients.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Client createClient(Client client) throws IOException {
        client.setId(UUID.randomUUID().toString());
        clients.add(client);
        saveClients();
        return client;
    }

    private void saveClients() throws IOException {
        repository.writeAll(CLIENTS_FILE, clients);
    }
}