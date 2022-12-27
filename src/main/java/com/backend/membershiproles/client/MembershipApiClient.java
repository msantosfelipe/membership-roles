package com.backend.membershiproles.client;

import com.backend.membershiproles.model.Team;
import com.backend.membershiproles.model.TeamDetail;
import com.backend.membershiproles.model.User;
import com.backend.membershiproles.model.UserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MembershipApiClient {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String usersUri = "/users";
    private static final String teamsUri = "/teams";

    @Value("${url.api.membership}")
    private String URL;

    public List<User> findAllUsers() {
        var objects = getObjects(usersUri);
        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, User.class))
                .collect(Collectors.toList());
    }

    public List<Team> findAllTeams() {
        var objects = getObjects(teamsUri);
        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, Team.class))
                .collect(Collectors.toList());
    }

    public UserDetail findUser(String user) {
        var object = getObject(usersUri + "/" + user);
        return mapper.convertValue(object, UserDetail.class);
    }

    public TeamDetail findUTeam(String team) {
        var object = getObject(teamsUri + "/" + team);
        return mapper.convertValue(object, TeamDetail.class);
    }

    public Object[] getObjects(String uri) {
        WebClient webClient = WebClient.create("https://" + URL);
        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object[].class)
                .log()
                .block();
    }

    public Object getObject(String uri) {
        WebClient webClient = WebClient.create("https://" + URL);
        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class)
                .log()
                .block();
    }
}
