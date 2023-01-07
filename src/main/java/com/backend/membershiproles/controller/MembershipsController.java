package com.backend.membershiproles.controller;

import com.backend.membershiproles.model.Team;
import com.backend.membershiproles.model.TeamDetail;
import com.backend.membershiproles.model.User;
import com.backend.membershiproles.model.UserDetail;
import com.backend.membershiproles.service.MembershipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/membership")
public class MembershipsController {

    @Autowired
    private MembershipsService membershipsService;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(membershipsService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Team>> findAllTeams() {
        return new ResponseEntity<>(membershipsService.findAllTeams(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetail> findUser(@PathVariable("id") String user) {
        return new ResponseEntity<>(membershipsService.findUser(user), HttpStatus.OK);
    }

    @GetMapping(value = "/teams/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDetail> findTeam(@PathVariable("id") String team) {
        return new ResponseEntity<>(membershipsService.findTeam(team), HttpStatus.OK);
    }

}
