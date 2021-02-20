FROM gradle:5.6.0-jdk11 as build
WORKDIR /comicpull
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY src src
COPY conf conf
RUN gradle shadowJar

FROM openjdk:11-jdk-slim
WORKDIR /comicpull
COPY --from=build /comicpull/build/libs/comicpull-0.0.1-all.jar app.jar
COPY conf conf
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
