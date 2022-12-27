package com.backend.membershiproles.model.dto;

import lombok.Data;

@Data
public class AssociationDto {
    private String roleCode;
    private String teamId;
    private String userId;
}
