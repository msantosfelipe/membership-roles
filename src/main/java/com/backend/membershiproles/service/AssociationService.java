package com.backend.membershiproles.service;

import com.backend.membershiproles.model.dto.AssociationDto;
import com.backend.membershiproles.model.entity.Association;
import com.backend.membershiproles.model.entity.Role;

import java.util.List;

public interface AssociationService {
    Association createAssociation(AssociationDto associationDto);
    Role findRoleForMembership(String teamId, String userId);
    List<Association> findMembershipForRole(String roleCode);
}
