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
    public void createAssociation(AssociationDto associationDto) {
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
        var teamId = UUID.fromString(associationDto.getTeamId());
        var userId = UUID.fromString(associationDto.getUserId());

        if (team.getTeamLeadId().equals(associationDto.getUserId())) {
            if (verifyExistinAssociation(roleId, teamId, userId)) {
                throw new BadRequestException("membership and role association already exists");
            }
            associationsRepository.save(new Association(roleId, teamId, userId));
        }

        team.getTeamMemberIds().forEach(i -> {
            if (i.equals(associationDto.getUserId())) {
                if (verifyExistinAssociation(roleId, teamId, userId)) {
                    throw new BadRequestException("membership and role association already exists");
                }

                associationsRepository.save(new Association(roleId, teamId, userId));
            }
        });
    }

    @Override
    public Role findRoleForMembership(String teamId, String userId) {
        var team = UUID.fromString(teamId);
        var user = UUID.fromString(userId);
        var association = associationsRepository.findByTeamAndUser(team, user);
        if (!association.isPresent()) {
            throw new ResourceNotFoundException("membership not found");
        }

        var role = rolesRepository.findById(association.get().getRole()).get();
        return role;
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
}
