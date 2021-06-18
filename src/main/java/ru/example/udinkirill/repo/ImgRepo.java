package ru.example.udinkirill.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.udinkirill.domain.Img;

public interface ImgRepo extends JpaRepository<Img, String> {


}
