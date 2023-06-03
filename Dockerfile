FROM gradle:8.1.1-jdk11
WORKDIR /
COPY . /
ENTRYPOINT ["./gradlew", "test"]