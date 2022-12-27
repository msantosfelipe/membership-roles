package com.backend.membershiproles.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "associations")
public class Association {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "role_id", nullable = false)
    private UUID role;
    @Column(name = "team_id", nullable = false)
    private UUID team;
    @Column(name = "user_id", nullable = false)
    private UUID user;

    public Association(UUID role, UUID team, UUID user) {
        this.role = role;
        this.team = team;
        this.user = user;
    }
}
