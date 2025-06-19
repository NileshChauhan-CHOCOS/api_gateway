
FROM eclipse-temurin:21

ARG APP_NAME
ENV APPLICATION_NAME=$APP_NAME

RUN echo "Building application: $APPLICATION_NAME"

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

EXPOSE 9091

CMD ["./mvnw", "spring-boot:run"]
