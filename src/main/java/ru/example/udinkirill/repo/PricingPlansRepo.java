package ru.example.udinkirill.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.udinkirill.domain.PricingPlans;
import ru.example.udinkirill.domain.Roles;


public interface PricingPlansRepo extends JpaRepository<PricingPlans, String> {


}
