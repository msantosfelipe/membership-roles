package com.backend.membershiproles.service.impl;

import com.backend.membershiproles.client.MembershipApiClient;
import com.backend.membershiproles.model.User;
import com.backend.membershiproles.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private MembershipApiClient client;

    @Override
    public List<User> GetAllUsers() {
        System.out.println("1");
        var a = client.GetAllUsers();
        System.out.println("4");
        return a;
    }
}
