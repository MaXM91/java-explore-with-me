FROM amazoncorretto:17
COPY target/*.jar stats-server.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=default", "-jar","/stats-server.jar"]