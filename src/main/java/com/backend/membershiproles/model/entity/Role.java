package com.backend.membershiproles.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonProperty(value = "role_code")
    @Column(name = "role_code", nullable = false)
    private String roleCode;
    @Column(nullable = false)
    private String name;
}
