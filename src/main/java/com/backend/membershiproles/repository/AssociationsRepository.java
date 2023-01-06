package com.backend.membershiproles.repository;

import com.backend.membershiproles.model.entity.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssociationsRepository extends JpaRepository<Association, UUID> {
    Optional<Association> findByRoleAndTeamAndUser(UUID role, UUID team, UUID user);
    Optional<List<Association>> findAllByRole(UUID role);
    Optional<Association> findByTeamAndUser(UUID team, UUID user);
}
