package com.example.backendapp;

//import com.example.backendapp.Invoice;
//import com.example.backendapp.InvoiceStatus;
//import com.example.backendapp.FileRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    private static final String INVOICES_FILE = "invoices.json";
    private final FileRepository<Invoice> repository;
    private List<Invoice> invoices;

    public InvoiceService(FileRepository<Invoice> repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() throws IOException {
        this.invoices = new ArrayList<>(repository.readAll(INVOICES_FILE, new TypeReference<>() {}));
    }

    public List<Invoice> getAllInvoices() {
        return invoices;
    }

    public Invoice createInvoice(String subscriptionId, double amount) throws IOException {
        Invoice invoice = new Invoice(
                UUID.randomUUID().toString(),
                subscriptionId,
                amount,
                LocalDate.now(),
                InvoiceStatus.PENDING
        );
        invoices.add(invoice);
        saveInvoices();
        return invoice;
    }

    public Optional<Invoice> payInvoice(String invoiceId) throws IOException {
        Optional<Invoice> invoiceOpt = invoices.stream().filter(i -> i.getId().equals(invoiceId)).findFirst();
        invoiceOpt.ifPresent(invoice -> invoice.setStatus(InvoiceStatus.PAID));
        saveInvoices();
        return invoiceOpt;
    }

    public List<Invoice> getInvoicesBySubscriptionId(String subscriptionId) {
        return invoices.stream().filter(i -> i.getSubscriptionId().equals(subscriptionId)).collect(Collectors.toList());
    }

    private void saveInvoices() throws IOException {
        repository.writeAll(INVOICES_FILE, invoices);
    }
}