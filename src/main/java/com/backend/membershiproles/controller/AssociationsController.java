package com.backend.membershiproles.controller;

import com.backend.membershiproles.exception.BadRequestException;
import com.backend.membershiproles.model.dto.AssociationDto;
import com.backend.membershiproles.model.dto.MembershipDto;
import com.backend.membershiproles.model.dto.RoleDto;
import com.backend.membershiproles.model.entity.Association;
import com.backend.membershiproles.model.entity.Role;
import com.backend.membershiproles.service.AssociationService;
import io.swagger.models.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/associations")
public class AssociationsController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AssociationService associationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Association> save(@RequestBody AssociationDto associationDto) {
        validateAssociationBody(associationDto);
        return new ResponseEntity<>(associationService.createAssociation(associationDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{team_id}/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> findRoleForMembership(@PathVariable("team_id") String teamId, @PathVariable("user_id") String userId) {
        return new ResponseEntity<>(associationService.findRoleForMembership(teamId, userId), HttpStatus.OK);
    }

    @GetMapping(value = "/{role_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MembershipDto>> findMembershipforRole(@PathVariable("role_code") String roleCode) {
        var assiciations = associationService.findMembershipForRole(roleCode);
        return new ResponseEntity<>(convertAssociationToMembershipDto(assiciations), HttpStatus.OK);
    }

    private RoleDto convertRoleToDto(Role role) throws ParseException {
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return roleDto;
    }

    private List<MembershipDto> convertAssociationToMembershipDto(List<Association> associations) throws ParseException {
        var membershipDtoList = new ArrayList<MembershipDto>();
        associations.forEach(i -> {
            membershipDtoList.add(new MembershipDto(i.getTeam().toString(), i.getUser().toString(), i.isTeamLead()));
        });
        return membershipDtoList;
    }

    private void validateAssociationBody(AssociationDto associationDto) {
        if (associationDto == null || associationDto.getRoleCode() == null || associationDto.getUserId() == null || associationDto.getUserId() == null) {
            throw new BadRequestException("invalid body");
        }
    }
}
