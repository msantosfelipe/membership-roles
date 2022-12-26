package com.backend.membershiproles.model;

import lombok.Data;

import java.util.List;

@Data
public class TeamDetail {
private String id;
private String name;
private String teamLeadId;
private List<String> teamMembersId;
}
