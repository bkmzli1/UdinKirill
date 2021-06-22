package ru.example.udinkirill.config;


import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.example.udinkirill.domain.Img;
import ru.example.udinkirill.model.RoleServiceModel;
import ru.example.udinkirill.model.UserRegisterBindingModel;
import ru.example.udinkirill.repo.ImgRepo;
import ru.example.udinkirill.services.impl.RoleService;
import ru.example.udinkirill.services.impl.UserService;

import java.io.IOException;

@Component
@CrossOrigin(origins = "http://localhost:4200")
public class InitialDataLoader implements ApplicationRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ImgRepo imgRepo;
    @Value(value = "classpath:"+"img/"+"logo.jpg")
    Resource schemaFile;

    @Autowired
    public InitialDataLoader(RoleService roleService, UserService userService, ModelMapper modelMapper, ImgRepo imgRepo) {
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.imgRepo = imgRepo;
    }

    public void run(ApplicationArguments args) throws IOException {
        RoleServiceModel userRole = this.roleService.findByAuthority("USER");
        RoleServiceModel executorRole = this.roleService.findByAuthority("EXECUTOR");
        RoleServiceModel adminRole = this.roleService.findByAuthority("ADMIN");

        UserRegisterBindingModel userRoot = this.userService.findByUsername("root");
        UserRegisterBindingModel userExecutor = this.userService.findByUsername("Все исполнители");

        if (userRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("USER");
            this.roleService.addRole(roleServiceModel);
        }
        if (executorRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("executor".toUpperCase());
            this.roleService.addRole(roleServiceModel);

        }

        if (adminRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("ADMIN");
            this.roleService.addRole(roleServiceModel);
        }

        if (userRoot == null) {
            UserRegisterBindingModel user = new UserRegisterBindingModel();
            user.setPassword("root");
            user.setFirstName("root");
            user.setLastName("root");
            user.setMiddleName("root");
            user.setEmail("-");
            user.setUsername("root");
            user.setAdmin(true);
            this.userService.create(user);
        }
        if (userExecutor == null) {
            UserRegisterBindingModel user = new UserRegisterBindingModel();
            user.setPassword("dqwfdwsfdwfws");
            user.setEmail("-");
            user.setUsername("Все исполнители");
            user.setLastName("Все исполнители");
            user.setFirstName(" ");
            user.setMiddleName(" ");
            user.setExecutor(true);
            this.userService.create(user);
        }

        Img byName = imgRepo.findByName("logo.jpg");
        if (byName == null) {

            Img img = new Img();
            img.setId("logo.jpg");
            img.setName("logo.jpg");
            byte[] bytes = IOUtils.toByteArray(schemaFile.getInputStream());
            img.setBite(bytes);
            imgRepo.save(img);
        }
    }
}