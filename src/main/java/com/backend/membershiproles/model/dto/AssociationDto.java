package com.backend.membershiproles.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssociationDto {
    @JsonProperty(value = "role_code")
    private String roleCode;
    @JsonProperty(value = "team_id")
    private String teamId;
    @JsonProperty(value = "user_id")
    private String userId;
}
