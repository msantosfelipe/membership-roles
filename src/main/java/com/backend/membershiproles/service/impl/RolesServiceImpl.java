package com.backend.membershiproles.service.impl;

import com.backend.membershiproles.exception.BadRequestException;
import com.backend.membershiproles.model.entity.Role;
import com.backend.membershiproles.repository.RolesRepository;
import com.backend.membershiproles.service.RolesService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public List<Role> findAll() {
        return rolesRepository.findAll();
    }

    @Override
    public Role createRole(Role role) {
        if (Strings.isBlank(role.getRoleCode()) || Strings.isEmpty(role.getRoleCode())) {
            var roleCode = role.getName().trim().toLowerCase().replaceAll(" ", "_");
            role.setRoleCode(roleCode);
        }

        if (rolesRepository.countByRoleCode(role.getRoleCode()) > 0) {
            throw new BadRequestException("role already exists");
        }

        return rolesRepository.save(role);
    }
}
