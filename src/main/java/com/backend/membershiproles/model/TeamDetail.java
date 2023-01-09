package com.backend.membershiproles.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeamDetail {
private String id;
private String name;
private String teamLeadId;
private List<String> teamMemberIds;
}
