package com.example.backendapp;

//import com.example.backendapp.Subscription;
//import com.example.backendapp.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody CreateSubscriptionRequest request) throws IOException {
        Subscription newSubscription = subscriptionService.createSubscription(request.clientId(), request.planId());
        return new ResponseEntity<>(newSubscription, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    public record CreateSubscriptionRequest(String clientId, String planId) {}
}