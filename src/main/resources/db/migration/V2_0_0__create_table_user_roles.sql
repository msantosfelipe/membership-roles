CREATE TABLE IF NOT EXISTS associations(
    id      UUID PRIMARY KEY,
    role_id UUID NOT NULL,
    team_id UUID NOT NULL,
    user_id UUID NOT NULL,

    FOREIGN KEY (role_id) REFERENCES Roles(id)
);
