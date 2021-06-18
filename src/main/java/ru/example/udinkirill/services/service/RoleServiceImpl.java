package ru.example.udinkirill.services.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.udinkirill.domain.Roles;
import ru.example.udinkirill.model.RoleServiceModel;
import ru.example.udinkirill.repo.RoleRepo;
import ru.example.udinkirill.services.impl.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepo roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        Roles roles = this.roleRepository.findByAuthority(authority);
        RoleServiceModel roleModel = null;
        if (roles != null) {
            roleModel = this.modelMapper.map(roles, RoleServiceModel.class);
        }
        return roleModel;
    }

    @Override
    public void addRole(RoleServiceModel roleServiceModel) {
        Roles roles = this.modelMapper.map(roleServiceModel, Roles.class);
        this.roleRepository.save(roles);
    }
}
