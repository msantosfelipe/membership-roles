package com.backend.membershiproles.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssociationService {
    @Value("${role.default}")
    private String defaultRole;


}
