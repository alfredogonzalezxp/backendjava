package com.example.backendapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    private String id;
    private String clientId;
    private String planId;
    private LocalDate startDate;
    private SubscriptionStatus status;
}