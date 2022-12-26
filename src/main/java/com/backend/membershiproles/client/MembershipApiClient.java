package com.backend.membershiproles.client;

import com.backend.membershiproles.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import reactor.core.publisher.Mono;

import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MembershipApiClient {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String UsersUri = "/users";

    @Value("${url.api.membership}")
    private String URL;

    public List<User> GetAllUsers() {
        var objects = getObjects(UsersUri);
        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, User.class))
                .collect(Collectors.toList());
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
}
