package ru.example.udinkirill.services.impl;


import ru.example.udinkirill.model.RoleServiceModel;

public interface RoleService {

    RoleServiceModel findByAuthority(String authority);

    void addRole(RoleServiceModel roleServiceModel);
}
