# membership-roles

## How it works
There are two tables (migrations on src/resources/db/migration):
- roles - id, role_code, name
- associations - id, role_id, team_id, user_id, team_lead

When a role is assigned to a team member a new record is created in associations table.
If no role is passed than dev role is set by default.




## How to run
- Must have Docker installed
- Run make command `make run-app`

## Documentation
- Swagger (ref: https://springdoc.org/v2/#migrating-from-springfox):
  - http://localhost:8080/membership-roles/swagger-ui/index.html


#### Next step suggestions
- Suggestion to return detailed information of users/teams at once, for example `https://{amazon-html}/users?detail=true`
- Implement edit and delete funcionality over associations

-------
TODO
- env variables
- testes
- fix docker
