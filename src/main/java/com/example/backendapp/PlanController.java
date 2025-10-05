package com.example.backendapp;

//import com.example.backendapp.Plan;
//import com.example.backendapp.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) throws IOException {
        Plan newPlan = planService.createPlan(plan);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }
}