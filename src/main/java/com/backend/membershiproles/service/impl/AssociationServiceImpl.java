package com.backend.membershiproles.service.impl;

import com.backend.membershiproles.client.MembershipApiClient;
import com.backend.membershiproles.exception.BadRequestException;
import com.backend.membershiproles.exception.ResourceNotFoundException;
import com.backend.membershiproles.model.dto.AssociationDto;
import com.backend.membershiproles.model.entity.Association;
import com.backend.membershiproles.model.entity.Role;
import com.backend.membershiproles.repository.AssociationsRepository;
import com.backend.membershiproles.repository.RolesRepository;
import com.backend.membershiproles.service.AssociationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssociationServiceImpl implements AssociationService {


    @Autowired
    private AssociationsRepository associationsRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private MembershipApiClient client;

    @Value("${role.default}")
    private String defaultRole;

    @Override
    public Association createAssociation(AssociationDto associationDto) {
        if (Strings.isEmpty(associationDto.getRoleCode())) {
            associationDto.setRoleCode(defaultRole);
        }

        var role = rolesRepository.findByRoleCode(associationDto.getRoleCode());
        if (!role.isPresent()) {
            throw new ResourceNotFoundException("role not found");
        }

        var team = client.findTeam(associationDto.getTeamId());
        if (team == null) {
            throw new ResourceNotFoundException("team not found");
        }

        var roleId = role.get().getId();
        var teamId = convertStringToUuid(associationDto.getTeamId());
        var userId = convertStringToUuid(associationDto.getUserId());

        if (verifyExistinAssociation(roleId, teamId, userId)) {
            throw new BadRequestException("membership and role association already exists");
        }

        if (team.getTeamLeadId().equals(associationDto.getUserId())) {
            return associationsRepository.save(new Association(roleId, teamId, userId));
        }

        for (int i = 0; i < team.getTeamMemberIds().size(); i++) {
            var teamUserId = team.getTeamMemberIds().get(i);
            if (teamUserId.equals(associationDto.getUserId())) {
                return associationsRepository.save(new Association(roleId, teamId, userId));
            }
        }

        throw new ResourceNotFoundException("user not found in team");
    }

    @Override
    public Role findRoleForMembership(String teamId, String userId) {
        var team = convertStringToUuid(teamId);
        var user = convertStringToUuid(userId);
        var association = associationsRepository.findByTeamAndUser(team, user);
        if (!association.isPresent()) {
            throw new ResourceNotFoundException("membership not found");
        }

        return rolesRepository.findById(association.get().getRole()).get();
    }

    @Override
    public List<Association> findMembershipForRole(String roleCode) {
        var role = rolesRepository.findByRoleCode(roleCode);
        if (!role.isPresent()) {
            throw new ResourceNotFoundException("role not found");
        }

        var associations = associationsRepository.findAllByRole(role.get().getId());
        if (!associations.isPresent() || associations.stream().count() == 0) {
            throw new ResourceNotFoundException("no role was found for this membership");
        }

        return associations.get();
    }

    private boolean verifyExistinAssociation(UUID role, UUID team, UUID user) {
       return associationsRepository.findByRoleAndTeamAndUser(role, team, user).isPresent();
    }

    private UUID convertStringToUuid(String value) {
        try {
            return UUID.fromString(value);
        } catch (Exception e) {
            throw new BadRequestException("invalid uuid");
        }
    }
}
