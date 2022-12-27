package com.backend.membershiproles.controller;

import com.backend.membershiproles.model.dto.RoleDto;
import com.backend.membershiproles.model.entity.Role;
import com.backend.membershiproles.service.RolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RolesController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    RolesService rolesService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Role> listAll(){
        return rolesService.findAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Role save(@RequestBody RoleDto roleDto){
        var role = convertToEntity(roleDto);
        return rolesService.createRole(role);
    }

    private Role convertToEntity(RoleDto roleDto) throws ParseException {
        Role role = modelMapper.map(roleDto, Role.class);
        return role;
    }

}
