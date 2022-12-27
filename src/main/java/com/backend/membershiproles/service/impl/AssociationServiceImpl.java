package com.backend.membershiproles.service.impl;

import com.backend.membershiproles.client.MembershipApiClient;
import com.backend.membershiproles.model.dto.AssociationDto;
import com.backend.membershiproles.model.entity.Association;
import com.backend.membershiproles.repository.AssociationsRepository;
import com.backend.membershiproles.repository.RolesRepository;
import com.backend.membershiproles.service.AssociationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public void createAssociation(AssociationDto associationDto) {
        if (Strings.isEmpty(associationDto.getRoleCode())) {
            associationDto.setRoleCode(defaultRole);
        }

        var role = rolesRepository.findByRoleCode(associationDto.getRoleCode());
        if (role == null) {
            // TODO throw role exception not found
            return;
        }

        var team = client.findUTeam(associationDto.getTeamId());
        if (team == null) {
            // TODO throu team not found exception
            return;
        }

        var roleId = role.getId();
        var teamId = UUID.fromString(associationDto.getTeamId());
        var userId = UUID.fromString(associationDto.getUserId());

        if (team.getTeamLeadId().equals(associationDto.getUserId())) {
            if (verifyExistinAssociation(roleId, teamId, userId)) {
                // TODO throu already exists exception
                return;
            }
            associationsRepository.save(new Association(roleId, teamId, userId));
        }

        team.getTeamMemberIds().forEach(i -> {
            if (i == associationDto.getUserId()) {
                if (verifyExistinAssociation(roleId, teamId, userId)) {
                    // TODO throw already exists exception
                    return;
                }
                // TODO else, adicionar na base
                associationsRepository.save(new Association(roleId, teamId, userId));
            }
        });
    }

    private boolean verifyExistinAssociation(UUID role, UUID team, UUID user) {
        var association = associationsRepository.findByRoleAndTeamAndUser(role, team, user);
        return association != null;
    }
}
