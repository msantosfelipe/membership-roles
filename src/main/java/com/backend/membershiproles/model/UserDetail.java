package com.backend.membershiproles.model;

import lombok.Data;

@Data
public class UserDetail {
    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String avatarUrl;
    private String location;

}
