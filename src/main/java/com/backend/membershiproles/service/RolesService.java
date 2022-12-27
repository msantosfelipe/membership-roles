package com.backend.membershiproles.service;

import com.backend.membershiproles.model.entity.Role;

import java.util.List;

public interface RolesService {
    List<Role> findAll();
    Role createRole(Role role);

}
