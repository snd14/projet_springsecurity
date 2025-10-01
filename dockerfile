
# Étape 1 : image légère Java (plus besoin de Maven ici)
#ENTRYPOINT ["java", "-jar", "app.jar"]
Étape 1 : builder le projet avec Maven
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image légère pour exécuter le JAR
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copier le jar généré depuis l'étape précédente
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port Spring Boot
EXPOSE 8080

#Commande de lancement
ENTRYPOINT ["java", "-jar", "app.jar"]