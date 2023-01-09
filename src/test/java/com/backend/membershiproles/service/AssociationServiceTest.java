package com.backend.membershiproles.service;

import com.backend.membershiproles.client.MembershipApiClient;
import com.backend.membershiproles.exception.BadRequestException;
import com.backend.membershiproles.exception.ResourceNotFoundException;
import com.backend.membershiproles.model.TeamDetail;
import com.backend.membershiproles.model.dto.AssociationDto;
import com.backend.membershiproles.model.entity.Association;
import com.backend.membershiproles.model.entity.Role;
import com.backend.membershiproles.repository.AssociationsRepository;
import com.backend.membershiproles.repository.RolesRepository;
import com.backend.membershiproles.service.impl.AssociationsServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AssociationServiceTest {
    @Mock
    private AssociationsRepository associationsRepository;
    @Mock
    private RolesRepository rolesRepository;
    @Mock
    private MembershipApiClient client;
    @InjectMocks
    private AssociationsServiceImpl associationService;

    private Association association;
    private List<Association> associationList;
    private Role role;
    private UUID roleId;
    private UUID teamId;
    private UUID userId;
    private TeamDetail teamDetail;
    private TeamDetail teamDetailTeamLead;

    @BeforeEach
    public void setup() {
        roleId = UUID.randomUUID();
        teamId = UUID.randomUUID();
        userId = UUID.randomUUID();
        association = new Association(roleId, teamId, userId, false);
        associationList = new ArrayList<>(Arrays.asList(association));
        role = new Role(roleId, "dev", "Developer");
        teamDetailTeamLead = new TeamDetail(teamId.toString(), "Team Name", userId.toString(), new ArrayList<>(Arrays.asList(UUID.randomUUID().toString())));
        teamDetail = new TeamDetail(teamId.toString(), "Team Name", UUID.randomUUID().toString(), new ArrayList<>(Arrays.asList(userId.toString())));
    }

    @Test
    void createAssociationSuccessTeamLead() {
        var newId = UUID.randomUUID();
        var newAssociation = new Association(newId, role.getId(), teamId,userId, true);

        Mockito.when(rolesRepository.findByRoleCode(Mockito.anyString())).thenReturn(Optional.of(role));
        Mockito.when(client.findTeam(Mockito.anyString())).thenReturn(teamDetailTeamLead);
        Mockito.when(associationsRepository.findByRoleAndTeamAndUser(Mockito.any(UUID.class), Mockito.any(UUID.class), Mockito.any(UUID.class))).thenReturn(Optional.empty());
        Mockito.when(associationsRepository.save(Mockito.any(Association.class))).thenReturn(newAssociation);

        var createdAssociation = associationService.createAssociation(new AssociationDto("dev", teamId.toString(), userId.toString()));
        Assert.assertNotNull(createdAssociation);
        Assert.assertEquals(newId, createdAssociation.getId());
    }

    @Test
    void createAssociationSuccessNotTeamLead() {
        var newId = UUID.randomUUID();
        var newAssociation = new Association(newId, role.getId(), teamId,userId, false);

        Mockito.when(rolesRepository.findByRoleCode(Mockito.anyString())).thenReturn(Optional.of(role));
        Mockito.when(client.findTeam(Mockito.anyString())).thenReturn(teamDetail);
        Mockito.when(associationsRepository.findByRoleAndTeamAndUser(Mockito.any(UUID.class), Mockito.any(UUID.class), Mockito.any(UUID.class))).thenReturn(Optional.empty());
        Mockito.when(associationsRepository.save(Mockito.any(Association.class))).thenReturn(newAssociation);

        var createdAssociation = associationService.createAssociation(new AssociationDto("dev", teamId.toString(), userId.toString()));
        Assert.assertNotNull(createdAssociation);
        Assert.assertEquals(newId, createdAssociation.getId());
    }

    @Test
    void createAssociationExceptionRoleNotFound() {
        var exception = Assert.assertThrows(ResourceNotFoundException.class, () -> {
            associationService.createAssociation(new AssociationDto("dev", teamId.toString(), userId.toString()));
        });

        Assert.assertEquals("role not found", exception.getMessage());
    }

    @Test
    void createAssociationExceptionTeamNotFound() {
        Mockito.when(rolesRepository.findByRoleCode(Mockito.anyString())).thenReturn(Optional.of(role));

        var exception = Assert.assertThrows(ResourceNotFoundException.class, () -> {
            associationService.createAssociation(new AssociationDto("dev", teamId.toString(), userId.toString()));
        });

        Assert.assertEquals("team not found", exception.getMessage());
    }

    @Test
    void createAssociationExceptionAlreadyExists() {
        Mockito.when(rolesRepository.findByRoleCode(Mockito.anyString())).thenReturn(Optional.of(role));
        Mockito.when(client.findTeam(Mockito.anyString())).thenReturn(teamDetail);
        Mockito.when(associationsRepository.findByRoleAndTeamAndUser(Mockito.any(UUID.class), Mockito.any(UUID.class), Mockito.any(UUID.class))).thenReturn(Optional.of(new Association()));

        var exception = Assert.assertThrows(BadRequestException.class, () -> {
            associationService.createAssociation(new AssociationDto("dev", teamId.toString(), userId.toString()));
        });

        Assert.assertEquals("membership and role association already exists", exception.getMessage());
    }

    @Test
    void findRoleForMembershipSuccess() {
        Mockito.when(associationsRepository.findByTeamAndUser(Mockito.any(), Mockito.any())).thenReturn(Optional.of(association));
        Mockito.when(rolesRepository.findById(Mockito.any())).thenReturn(Optional.of(role));

        var returnRole = associationService.findRoleForMembership(teamId.toString(), userId.toString());
        Assert.assertNotNull(returnRole);
        Assert.assertEquals(role.getId(), returnRole.getId());
    }

    @Test()
    void findRoleForMembershipExceptionInvalidUuid() {
        var exception = Assert.assertThrows(BadRequestException.class, () -> {
            associationService.findRoleForMembership("abc", userId.toString());
        });

        Assert.assertEquals("invalid uuid", exception.getMessage());
    }

    @Test()
    void findRoleForMembershipExceptionMembershipNotFound() {
        Mockito.when(associationsRepository.findByTeamAndUser(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        var exception = Assert.assertThrows(ResourceNotFoundException.class, () -> {
            associationService.findRoleForMembership(teamId.toString(), userId.toString());
        });

        Assert.assertEquals("membership not found", exception.getMessage());
    }

    @Test
    void findMembershipForRoleSuccess() {
        Mockito.when(rolesRepository.findByRoleCode(Mockito.anyString())).thenReturn(Optional.of(role));
        Mockito.when(associationsRepository.findAllByRole(Mockito.any())).thenReturn(Optional.of(associationList));

        var returnRole = associationService.findMembershipForRole(role.getRoleCode());
        Assert.assertNotNull(returnRole);
        Assert.assertEquals(returnRole.size(), associationList.size());
    }

    @Test
    void findMembershipForRoleExceptionRoleNotFound() {
        Mockito.when(rolesRepository.findByRoleCode(Mockito.anyString())).thenReturn(Optional.empty());

        var exception = Assert.assertThrows(ResourceNotFoundException.class, () -> {
            associationService.findMembershipForRole(role.getRoleCode());
        });

        Assert.assertEquals("role not found", exception.getMessage());
    }

    @Test
    void findMembershipForRoleExceptionRoleNotFoundForMembership() {
        Mockito.when(rolesRepository.findByRoleCode(Mockito.anyString())).thenReturn(Optional.of(role));
        Mockito.when(associationsRepository.findAllByRole(Mockito.any())).thenReturn(Optional.empty());

        var exception = Assert.assertThrows(ResourceNotFoundException.class, () -> {
            associationService.findMembershipForRole(role.getRoleCode());
        });

        Assert.assertEquals("no role was found for this membership", exception.getMessage());
    }
}
