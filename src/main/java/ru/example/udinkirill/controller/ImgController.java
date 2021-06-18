package ru.example.udinkirill.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.example.udinkirill.domain.Img;
import ru.example.udinkirill.domain.User;
import ru.example.udinkirill.domain.enums.ImgType;
import ru.example.udinkirill.repo.ImgRepo;
import ru.example.udinkirill.repo.UserRepo;
import ru.example.udinkirill.services.impl.ImgService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@CrossOrigin
@RequestMapping("/img")
public class ImgController {
    private final ImgRepo imgRepo;
    private final ImgService imgService;
    private final UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public ImgController(ImgRepo imgRepo, ImgService imgService, UserRepo userRepo) {
        this.imgRepo = imgRepo;
        this.imgService = imgService;
        this.userRepo = userRepo;
    }

    @PostMapping(value = "/{type}")
    @ResponseBody
    public Object addImg(@RequestBody @Valid MultipartFile[] mfImg, @PathVariable String type, Authentication authentication) {
        File fileUploadPath = new File(uploadPath);
        User user = null;
        try {
            user = (User) authentication.getPrincipal();
        } catch (NullPointerException nullPointerException) {
        }


        Set<Img> images = new HashSet<>();
        try {
            for (MultipartFile mf : mfImg) {
                String upFile = "/img";
                File uploadPDir = new File(fileUploadPath.getAbsolutePath() + upFile);
                if (!uploadPDir.exists()) {
                    uploadPDir.mkdirs();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + mf.getOriginalFilename();

                try {
                    mf.transferTo(new File(uploadPDir + "/" + resultFilename));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Img imgDB = new Img();
                imgDB.setName(mf.getResource().getFilename());
                imgDB.setImgType(ImgType.valueOf(type));
                imgDB.setImg("static" + upFile + "/" + resultFilename);

                images.add(imgDB);
            }
            AtomicBoolean profile = new AtomicBoolean(false);
            User finalUser = user;
            images.forEach(img -> {
                imgService.imgCrate(img);
                if (finalUser != null)
                    switch (img.getImgType()) {
                        case PROFILE_BACKGROUND:
                            finalUser.setImgFon(img);
                            userRepo.save(finalUser);
                            profile.set(true);
                            break;
                        case PROFILE_IMG:
                            finalUser.setImg(img);
                            userRepo.save(finalUser);
                            profile.set(true);
                            break;
                    }
            });
                if (profile.get()) {
                return user;
            }

        } catch (NullPointerException e) {
            return null;
        }
        Set<String> imgIDs = new HashSet<>();
        images.forEach(img -> imgIDs.add(img.getId()));
        return imgIDs;
    }
}
