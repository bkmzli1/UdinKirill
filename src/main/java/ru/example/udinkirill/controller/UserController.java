package ru.example.udinkirill.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.udinkirill.domain.User;
import ru.example.udinkirill.model.UserRegisterBindingModel;
import ru.example.udinkirill.repo.ImgRepo;
import ru.example.udinkirill.repo.UserRepo;
import ru.example.udinkirill.services.impl.UserService;


import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;


    private final UserRepo userRepository;
    private final ImgRepo imgRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, UserRepo userRepository, ImgRepo imgRepository,
                          ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.imgRepository = imgRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @PostMapping(value = "/reg")
    @ResponseBody
    public Map<Object, Object> registerConfirm(@RequestBody @Valid UserRegisterBindingModel userRegisterBindingModel,
                                               BindingResult bindingResult) {

        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        } else {
            UserRegisterBindingModel userServiceModel = this.modelMapper
                    .map(userRegisterBindingModel, UserRegisterBindingModel.class);
            this.userService.create(userServiceModel);
            strings.put("error", null);
        }
        return strings;
    }

    @GetMapping
    @RequestMapping("/user")
    public User user(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return user;
        } catch (NullPointerException npe) {

        }
        return null;

    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public User userID(@PathVariable String id) {
        User user = userRepository.findOneById(id);
        return user;
    }

    @PostMapping("/save")
    @ResponseBody
    public User userSave(@RequestBody() UserRegisterBindingModel userRegisterBindingModel,
                         Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        System.out
                .println(bCryptPasswordEncoder.matches(userRegisterBindingModel.getOldPassword(), user.getPassword()));
        ;
        if (bCryptPasswordEncoder.matches(userRegisterBindingModel.getOldPassword(), user.getPassword())) {
            user.setFirstName(userRegisterBindingModel.getFirstName());
            user.setMiddleName(userRegisterBindingModel.getMiddleName());
            user.setLastName(userRegisterBindingModel.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(userRegisterBindingModel.getPassword()));
            userRepository.save(user);
        }
        return user;
    }

    @GetMapping("/executor")
    public Set<User> userEx() {
        Set<User> users = new TreeSet<User>(Comparator.comparing(User::getUsername));
        List<User> userByRole = userRepository.findUsersByAuthoritiesAuthority("EXECUTOR");
        users.addAll(userByRole);
        return users;
    }

    @GetMapping("/usersss")
    public Object user(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/allusers")
    public Object allUsers() {
        return userRepository.findAll();
    }

}