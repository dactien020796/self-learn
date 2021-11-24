# self-learn project
A project that use cloud-native solution to improve my technical

# Install redis cache
`docker run -p 6379:6379 -d redis`

# Docker command to start application
`docker run --name self-learn -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/db_example -e SPRING_FLYWAY_URL=jdbc:mysql://host.docker.internal:3306 -e SPRING_REDIS_HOST=host.docker.internal dactien020796/self-learn`