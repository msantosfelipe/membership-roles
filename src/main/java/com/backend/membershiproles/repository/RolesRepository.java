package com.backend.membershiproles.repository;

import com.backend.membershiproles.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Role, UUID> {
    long countByRoleCode(String roleCode);
    Role findByRoleCode(String roleCode);
}
