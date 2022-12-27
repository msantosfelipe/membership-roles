# membership-roles

## How it works
There are two tables:
- roles - id, role_code, name
- associations - id, role_id, team_id, user_id

When a role is assigned to a team member a new record is created in associations table


## How to run
- Must have Docker installed
- Run make command `make run-app`

#### Next step suggestions
Implement edit and delete funcionality over associations

-------
CREATE TABLE IF NOT EXISTS users_roles(
role UUID
team UUID
user UUID   
);

POST /membership
{
role_code // not required, defauld = dev
team_id
user_id
}

GET /membership/team_id/user_id

GET /membership/role