package com.backend.membershiproles.controller;

import com.backend.membershiproles.model.dto.RoleDto;
import com.backend.membershiproles.model.entity.Role;
import com.backend.membershiproles.service.RolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Role>> listAll(){
        return new ResponseEntity<>(rolesService.findAll(), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> save(@RequestBody RoleDto roleDto){
        var role = convertToEntity(roleDto);
        return new ResponseEntity<>(rolesService.createRole(role), HttpStatus.CREATED);
    }

    private Role convertToEntity(RoleDto roleDto) throws ParseException {
        Role role = modelMapper.map(roleDto, Role.class);
        return role;
    }

}
