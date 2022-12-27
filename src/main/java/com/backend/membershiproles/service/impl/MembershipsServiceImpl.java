package com.backend.membershiproles.service.impl;

import com.backend.membershiproles.client.MembershipApiClient;
import com.backend.membershiproles.model.Team;
import com.backend.membershiproles.model.TeamDetail;
import com.backend.membershiproles.model.User;
import com.backend.membershiproles.model.UserDetail;
import com.backend.membershiproles.service.MembershipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipsServiceImpl implements MembershipsService {
    @Autowired
    private MembershipApiClient client;

    @Override
    public List<User> findAllUsers() {
        return client.findAllUsers();
    }

    @Override
    public List<Team> findAllTeams() {
        return client.findAllTeams();
    }

    @Override
    public UserDetail findUser(String user) {
        return client.findUser(user);
    }

    @Override
    public TeamDetail findTeam(String team) {
        return client.findUTeam(team);
    }
}
