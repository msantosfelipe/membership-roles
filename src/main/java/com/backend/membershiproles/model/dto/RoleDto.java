package com.backend.membershiproles.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleDto {
    @JsonProperty(value = "role_code")
    private String roleCode;
    private String name;

}
