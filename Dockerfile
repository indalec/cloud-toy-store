FROM maven:3.9.9-ibm-semeru-23-jammy AS build

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:23.0.1_11-jre-alpine

COPY --from=build /target/MyWorkAPI-0.0.1-SNAPSHOT.jar MyWorkAPI.jar

EXPOSE 8080

CMD ["java", "-jar", "MyWorkAPI.jar"]
