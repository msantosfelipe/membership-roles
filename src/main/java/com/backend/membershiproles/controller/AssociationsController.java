package com.backend.membershiproles.controller;

import com.backend.membershiproles.exception.BadRequestException;
import com.backend.membershiproles.model.dto.AssociationDto;
import com.backend.membershiproles.model.dto.MembershipDto;
import com.backend.membershiproles.model.entity.Association;
import com.backend.membershiproles.model.entity.Role;
import com.backend.membershiproles.service.AssociationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/associations")
@Tag(name="Associations", description = "Relationship of roles and membership")
public class AssociationsController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AssociationService associationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Assign a role to a team member")
    public ResponseEntity<Association> save(@RequestBody AssociationDto associationDto) {
        validateAssociationBody(associationDto);
        return new ResponseEntity<>(associationService.createAssociation(associationDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{team_id}/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Look up a role for a membership")
    public ResponseEntity<Role> findRoleForMembership(@PathVariable("team_id") String teamId, @PathVariable("user_id") String userId) {
        return new ResponseEntity<>(associationService.findRoleForMembership(teamId, userId), HttpStatus.OK);
    }

    @GetMapping(value = "/{role_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Look up memberships for a role")
    public ResponseEntity<List<MembershipDto>> findMembershipforRole(@PathVariable("role_code") String roleCode) {
        var associations = associationService.findMembershipForRole(roleCode);
        return new ResponseEntity<>(convertAssociationToMembershipDto(associations), HttpStatus.OK);
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
