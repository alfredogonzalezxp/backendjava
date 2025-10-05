package com.example.backendapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    private String id;
    private String name;
    private double price;
    private BillingCycle billingCycle;
}