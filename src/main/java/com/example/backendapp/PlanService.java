package com.example.backendapp;

//import com.example.backendapp.Plan;
//import com.example.backendapp.FileRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlanService {
    private static final String PLANS_FILE = "plans.json";
    private final FileRepository<Plan> repository;
    private List<Plan> plans;

    public PlanService(FileRepository<Plan> repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() throws IOException {
        this.plans = new ArrayList<>(repository.readAll(PLANS_FILE, new TypeReference<>() {}));
    }

    public List<Plan> getAllPlans() {
        return plans;
    }

    public Optional<Plan> getPlanById(String id) {
        return plans.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public Plan createPlan(Plan plan) throws IOException {
        plan.setId(UUID.randomUUID().toString());
        plans.add(plan);
        repository.writeAll(PLANS_FILE, plans);
        return plan;
    }
}