package com.example.backendapp;

//import com.example.backendapp.Invoice;
//import com.example.backendapp.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @PostMapping("/{invoiceId}/pay")
    public ResponseEntity<Invoice> payInvoice(@PathVariable String invoiceId) throws IOException {
        return invoiceService.payInvoice(invoiceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}