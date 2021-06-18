package ru.example.udinkirill.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.udinkirill.domain.Img;
import ru.example.udinkirill.domain.LeaveARequest;

public interface LeaveARequestRepo extends JpaRepository<LeaveARequest, String> {


}
