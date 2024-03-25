FROM amazoncorretto:17
COPY target/*.jar ewm-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=default", "-jar","/ewm-service.jar"]