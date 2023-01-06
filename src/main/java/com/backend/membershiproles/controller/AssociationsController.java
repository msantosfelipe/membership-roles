package com.backend.membershiproles.controller;

import com.backend.membershiproles.model.dto.AssociationDto;
import com.backend.membershiproles.model.dto.RoleDto;
import com.backend.membershiproles.model.entity.Association;
import com.backend.membershiproles.model.entity.Role;
import com.backend.membershiproles.service.AssociationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/associations")
public class AssociationsController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AssociationService associationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody AssociationDto associationDto){
        associationService.createAssociation(associationDto);
    }

    @GetMapping(value = "/{team_id}/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RoleDto findRoleForMembership(@PathVariable("team_id") String teamId, @PathVariable("user_id") String userId){
        var role = associationService.findRoleForMembership(teamId, userId);
        return convertRoleToDto(role);
    }

    @GetMapping(value = "/{role_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Association findMembershipforRole(@PathVariable("role_code") String roleCode){
        return associationService.findMembershipForRole(roleCode);
    }

    private RoleDto convertRoleToDto(Role role) throws ParseException {
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return roleDto;
    }

}
