package ru.example.udinkirill.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.udinkirill.domain.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {

    User findUserById(String id);

    User findOneByUsername(String username);

    User findByEmail(String email);

    User findOneById(String id);


    List<User> findUsersByAuthoritiesAuthority(String authorities);
}
