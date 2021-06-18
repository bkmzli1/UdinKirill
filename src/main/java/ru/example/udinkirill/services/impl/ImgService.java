package ru.example.udinkirill.services.impl;

import ru.example.udinkirill.domain.Img;
import ru.example.udinkirill.model.ImgCreateModel;

public interface ImgService {
   Img imgCrate(ImgCreateModel taskCreate);
   Img imgCrate(Img img);
}
