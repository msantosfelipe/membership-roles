package com.backend.membershiproles.service;

import com.backend.membershiproles.model.Team;
import com.backend.membershiproles.model.TeamDetail;
import com.backend.membershiproles.model.User;
import com.backend.membershiproles.model.UserDetail;

import java.util.List;

public interface MembershipsService {
    List<User> findAllUsers();
    List<Team> findAllTeams();
    UserDetail findUser(String user);
    TeamDetail findTeam(String team);

}
