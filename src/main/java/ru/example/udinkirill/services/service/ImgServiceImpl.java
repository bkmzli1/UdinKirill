package ru.example.udinkirill.services.service;

import org.springframework.stereotype.Service;
import ru.example.udinkirill.domain.Img;
import ru.example.udinkirill.model.ImgCreateModel;
import ru.example.udinkirill.repo.ImgRepo;
import ru.example.udinkirill.services.impl.ImgService;

@Service
public class ImgServiceImpl implements ImgService {
    private final ImgRepo imgRepo;

    public ImgServiceImpl(ImgRepo imgRepo) {

        this.imgRepo = imgRepo;
    }

    @Override
    public Img imgCrate(ImgCreateModel taskCreate) {
        Img img = new Img();
        img.setImg(taskCreate.getImg());
        img.setImgType(taskCreate.getImgType());
        img.setName(taskCreate.getName());
        return img;
    }

    @Override
    public Img imgCrate(Img img) {
        imgRepo.save(img);
        return null;
    }

}
