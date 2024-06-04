package com.vendavaultecommerceproject.security.service.impl.role;


import com.vendavaultecommerceproject.exception.exeception.DataAlreadyExistException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.security.dto.RoleDto;
import com.vendavaultecommerceproject.security.dto.RoleToUserDto;
import com.vendavaultecommerceproject.security.entity.AppUser;
import com.vendavaultecommerceproject.security.entity.Role;
import com.vendavaultecommerceproject.security.repository.AppUserRepository;
import com.vendavaultecommerceproject.security.repository.RoleRepository;
import com.vendavaultecommerceproject.security.service.main.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final AppUserRepository appUserRepository;

    @Override
    public String saveRole(RoleDto roleDto) throws DataAlreadyExistException {
        if (repository.findByName(roleDto.getName()).isPresent()){
            throw new DataAlreadyExistException("You have already added this role");
        }
        Role role = Role.builder()
                .name(roleDto.getName().toUpperCase())
                .build();
        repository.save(role);
        return "Role Added Successfully";
    }

    @Override
    public String addRoleToUser(RoleToUserDto roleToUserDto) throws DataNotFoundException, DataAlreadyExistException {
        Optional<AppUser> appUser = appUserRepository.findByEmail(roleToUserDto.getUserEmail());
        if (appUser.isEmpty()){
            throw new DataNotFoundException("There is no user with the provided email");
        }
        Optional<Role> role = repository.findByName(roleToUserDto.getRoleName());
        if (Objects.isNull(role)){
            throw new DataNotFoundException("There is no role with the entered name");
        }
        for (Role roles: appUser.get().getRoles()){
            if (roles.getName().equalsIgnoreCase(role.get().getName())){
                throw new DataAlreadyExistException("You have already added this role to this user");
            }
        }
        appUser.get().getRoles().add(role.get());
        return roleToUserDto.getRoleName()+" has been added to "+appUser.get().fullName();
    }
}
