package com.example.backendapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private String id;
    private String subscriptionId;
    private double amount;
    private LocalDate dueDate;
    private InvoiceStatus status;
}