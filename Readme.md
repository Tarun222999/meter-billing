 ## Prerequisites
    Docker Desktop running.

    ## Run the app (with the dev database)
    cd backend
    docker compose up -d          # from the repo root, starts Postgres
  on 5432
    $env:SPRING_PROFILES_ACTIVE = "local"
    .\mvnw.cmd spring-boot:run    # Flyway migrates, Hibernate validates,        
   Tomcat on 8080
    docker compose down           # stop it

    ## Run the tests
    cd backend
    .\mvnw.cmd test               # Testcontainers starts its own
  Postgres; no Compose needed