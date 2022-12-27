run-app: build-docker-image run-docker

build-docker-image:
	./mvnw clean package -DskipTests
	cp target/membership-roles-0.0.1-SNAPSHOT.jar src/main/resources/docker

run-docker:
	cd src/main/resources/docker/ && docker-compose up

stop-docker:
	cd src/main/resources/docker/ && docker-compose down

# For development purposes:
create-dev-db:
	docker run --name teste-postgres -e "POSTGRES_USER=compose-postgres" -e "POSTGRES_PASSWORD=compose-postgres" -p 5432:5432 -v $(CURDIR)/docker/data:/var/lib/postgresql/data -d postgres

run-dev-db:
	docker start teste-postgres

stop-dev-db:
	docker stop teste-postgres

