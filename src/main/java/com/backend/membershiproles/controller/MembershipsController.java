package com.backend.membershiproles.controller;

import com.backend.membershiproles.model.Team;
import com.backend.membershiproles.model.TeamDetail;
import com.backend.membershiproles.model.User;
import com.backend.membershiproles.model.UserDetail;
import com.backend.membershiproles.service.MembershipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/membership")
public class MembershipsController {

    @Autowired
    private MembershipsService membershipsService;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAllUsers() {
        return membershipsService.findAllUsers();
    }

    @GetMapping(value = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Team> findAllTeams() {
        return membershipsService.findAllTeams();
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDetail findUser(@PathVariable("id") String user) {
        return membershipsService.findUser(user);
    }

    @GetMapping(value = "/teams/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TeamDetail findTeam(@PathVariable("id") String team) {
        return membershipsService.findTeam(team);
    }

}
