package com.example.backendapp;

//import com.example.backendapp.Plan;
//import com.example.backendapp.Subscription;
//import com.example.backendapp.SubscriptionStatus;
//import com.example.backendapp.FileRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionService {
    private static final String SUBSCRIPTIONS_FILE = "subscriptions.json";
    private final FileRepository<Subscription> repository;
    private final ClientService clientService;
    private final PlanService planService;
    private final InvoiceService invoiceService;
    private List<Subscription> subscriptions;

    public SubscriptionService(FileRepository<Subscription> repository, ClientService clientService, PlanService planService, InvoiceService invoiceService) {
        this.repository = repository;
        this.clientService = clientService;
        this.planService = planService;
        this.invoiceService = invoiceService;
    }

    @PostConstruct
    public void init() throws IOException {
        this.subscriptions = new ArrayList<>(repository.readAll(SUBSCRIPTIONS_FILE, new TypeReference<>() {}));
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptions;
    }

    public Subscription createSubscription(String clientId, String planId) throws IOException {
        clientService.getClientById(clientId).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Plan plan = planService.getPlanById(planId).orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        Subscription subscription = new Subscription(
                UUID.randomUUID().toString(),
                clientId,
                planId,
                LocalDate.now(),
                SubscriptionStatus.ACTIVE
        );

        subscriptions.add(subscription);
        saveSubscriptions();

        // Generate first invoice
        invoiceService.createInvoice(subscription.getId(), plan.getPrice());

        return subscription;
    }

    private void saveSubscriptions() throws IOException {
        repository.writeAll(SUBSCRIPTIONS_FILE, subscriptions);
    }
}