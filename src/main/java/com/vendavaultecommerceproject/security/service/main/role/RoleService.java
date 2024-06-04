package com.vendavaultecommerceproject.security.service.main.role;
import com.vendavaultecommerceproject.exception.exeception.DataAlreadyExistException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.security.dto.RoleDto;
import com.vendavaultecommerceproject.security.dto.RoleToUserDto;


public interface RoleService {
    public String saveRole(RoleDto roleDto) throws DataAlreadyExistException;

    public String addRoleToUser(RoleToUserDto roleToUserDto) throws DataNotFoundException, DataAlreadyExistException;
}
