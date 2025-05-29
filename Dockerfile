# Étape 1 : Build avec Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copier les fichiers nécessaires pour le build
COPY pom.xml .
COPY src ./src

# Télécharger les dépendances et builder l'application
RUN mvn clean package -DskipTests

# Étape 2 : Image légère avec Java uniquement
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copier le .jar depuis l'image précédente
COPY --from=build /app/target/*.jar app.jar

# Exposer le port de ton application Spring Boot (par défaut 8080)
EXPOSE 8080

# Commande pour lancer ton app
ENTRYPOINT ["java", "-jar", "app.jar"]
