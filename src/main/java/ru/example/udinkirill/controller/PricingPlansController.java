package ru.example.udinkirill.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.udinkirill.domain.LeaveARequest;
import ru.example.udinkirill.domain.PricingPlans;
import ru.example.udinkirill.model.LeaveARequestModele;
import ru.example.udinkirill.model.PricingPlansModele;
import ru.example.udinkirill.repo.ImgRepo;
import ru.example.udinkirill.repo.LeaveARequestRepo;
import ru.example.udinkirill.repo.PricingPlansRepo;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin
public class PricingPlansController {
    private final PricingPlansRepo pricingPlansRepo;
    private final LeaveARequestRepo aRequestRepo;
    private final ImgRepo imgRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public PricingPlansController(PricingPlansRepo pricingPlansRepo,
                                  LeaveARequestRepo aRequestRepo, ImgRepo imgRepo,
                                  ModelMapper modelMapper) {
        this.pricingPlansRepo = pricingPlansRepo;
        this.aRequestRepo = aRequestRepo;
        this.imgRepo = imgRepo;
        this.modelMapper = modelMapper;
    }


    @PostMapping(value = "/crate")
    @ResponseBody
    public Map<Object, Object> registerConfirm(@RequestBody @Valid PricingPlansModele pricingPlansModele,
                                               BindingResult bindingResult) {
        Map<Object, Object> strings = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Set<String> errors = new TreeSet<>();
            bindingResult.getAllErrors().forEach(objectError -> errors.add(objectError.getDefaultMessage()));
            strings.put("error", errors);
            return strings;
        }

        PricingPlans pricingPlans = this.modelMapper.map(pricingPlansModele, PricingPlans.class);
        try {
            pricingPlans.setImg(this.imgRepo.getOne(pricingPlansModele.getImg()[0]));
        } catch (Exception e) {
            Set<String> errors = new TreeSet<>();
            errors.add("Ошибка изображения");
            strings.put("error", errors);
            return strings;
        }

        pricingPlansRepo.save(pricingPlans);
        return strings;


    }

    @GetMapping(value = "/inventory")
    @ResponseBody
    public Object inventory() {
        Set<PricingPlans> errors = new TreeSet<>(Comparator.comparing(PricingPlans::getTegUUI));
        errors.addAll(pricingPlansRepo.findAll());
        return errors;


    }

    @GetMapping(value = "/apileavearequest/{id}")
    @ResponseBody
    public Object leavearequest(@PathVariable String id) {
        return pricingPlansRepo.findById(id);


    }

    @PostMapping(value = "/apileavearequest")
    @ResponseBody
    public void leavearequest(@RequestBody LeaveARequestModele leave) {
        LeaveARequest leaveARequest = this.modelMapper.map(leave, LeaveARequest.class);
        leaveARequest.setPricingPlans(pricingPlansRepo.getOne(leave.getIdPP()));
        aRequestRepo.save(leaveARequest);

    }

    @GetMapping(value = "/prifil")
    @ResponseBody
    public Object prifil() {
        return aRequestRepo.findAll();

    }

    @DeleteMapping(value = "/prifil/{id}")
    @ResponseBody
    public Object prifilDel(@PathVariable String id) {
        aRequestRepo.deleteById(id);
        return aRequestRepo.findAll();
    }

    @DeleteMapping(value = "/apileavearequest/{id}")
    @ResponseBody
    public Object apileavearequestDel(@PathVariable String id) {

        aRequestRepo.findAll().stream().filter(leaveARequest -> leaveARequest.getPricingPlans().getId().equals(id)).forEach(
               leaveARequest -> aRequestRepo.delete(leaveARequest));

        pricingPlansRepo.deleteById(id);

        return pricingPlansRepo.findAll();
    }
}
