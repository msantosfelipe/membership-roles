build-docker-image:
	./mvnw clean package -DskipTests
	cp target/membership-roles-0.0.1-SNAPSHOT.jar docker

run-docker:
	cd docker/ && docker-compose up

stop-docker:
	cd docker/ && docker-compose down

create-dev-db:
	docker run --name teste-postgres -e "POSTGRES_USER=compose-postgres" -e "POSTGRES_PASSWORD=compose-postgres" -p 5432:5432 -v $(CURDIR)/docker/data:/var/lib/postgresql/data -d postgres

run-dev-db:
	docker start teste-postgres

stop-dev-db:
	docker stop teste-postgres