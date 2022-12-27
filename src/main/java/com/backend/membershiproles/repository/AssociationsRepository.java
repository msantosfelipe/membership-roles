package com.backend.membershiproles.repository;

import com.backend.membershiproles.model.entity.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssociationsRepository extends JpaRepository<Association, UUID> {
    Association findByRoleAndTeamAndUser(UUID role, UUID team, UUID user);
}
