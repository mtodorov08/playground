#!/bin/bash
set -e

echo "Building microservices..."

mvn -q -DskipTests package --file microservices/eureka-server/pom.xml
mvn -q -DskipTests install --file microservices/book-service/pom.xml
mvn -q -DskipTests package --file microservices/api-gateway/pom.xml
mvn -q -DskipTests package --file microservices/review-service/pom.xml

echo "Starting docker-compose..."
docker compose -f .devcontainer/docker-compose.yml up --build -d
