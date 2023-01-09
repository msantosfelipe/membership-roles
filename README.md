# membership-roles

## How it works
There are two tables (migrations on src/resources/db/migration):
- roles
- associations

When a role is assigned to a team member a new record is created in associations table.
There are three roles predefined in the system, but you can create new ones . If no role is passed when assigning a role to a team member than dev role is set by default.

## How to run
- Must have Docker and Maven installed
- Run make command to create db container `make create-dev-db`
- Run Maven commands to clean and package `mvn clean package`
- Start the application `java -jar target/membership-roles-0.0.1-SNAPSHOT`
- Access Swagger to view/test endpoints

## Documentation
- Swagger (ref: https://springdoc.org/v2/#migrating-from-springfox):
  - http://localhost:8080/membership-roles/swagger-ui/index.html
- Principal endpoints:
  - Create role: POST /membership-roles/roles
  - Create associations: POST /membership-roles/associations
  - Find Membership for Role: GET /membership-roles/associations/{role}
  - Find Role For Membership: GET /membership-roles/associations/teamId/{userId}
- Postman collection with all endpoints at /src/main/resources/membership-roles.postman_collection.json

#### Next steps and suggestions
- Fix docker container. Running docker-compose is creating the container and starting the application (`make run-app`), but calling any endpoint returns not found, probably some configuration is missing
- Suggestion to return detailed information of users/teams at once, for example `https://{amazon-html}/users?detail=true`
- Implement edit and delete funcionality over associations
- Move some hardcoded variables to environment variables
-------
