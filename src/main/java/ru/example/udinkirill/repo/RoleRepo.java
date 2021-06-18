package ru.example.udinkirill.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.udinkirill.domain.Roles;

public interface RoleRepo extends JpaRepository<Roles, String> {
    Roles findByAuthority(String authority);
}
