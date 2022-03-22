FROM gradle:jdk17 AS BUILD
WORKDIR /chicken_build
COPY . /chicken_build
RUN gradle bootJar

FROM openjdk:17-alpine
WORKDIR /chicken

# Add docker-compose-wait tool -------------------
ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

COPY --from=BUILD /chicken_build/build/libs/*.jar /chicken/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]