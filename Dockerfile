FROM java:8-jre-alpine
ADD target/spring-postg-dock.jar spring-postg-dock.jar
EXPOSE 8991
ENTRYPOINT ["java", "-jar", "spring-postg-dock.jar"]