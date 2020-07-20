FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -Dmaven.test.skip=true 
FROM openjdk:8-jre-alpine
WORKDIR /app
CMD ls
COPY --from=MAVEN_BUILD /build/target/NightRidePlanner.jar /app/

ENTRYPOINT ["java", "-jar", "NightRidePlanner.jar"]