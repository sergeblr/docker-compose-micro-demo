# micro-items runs under JVM
FROM anapsix/alpine-java
MAINTAINER sergeblr
COPY --from=cafemenu_mvn:latest /home/cafemenu/micro-items/target/micro-items-1.0.0-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "micro-items-1.0.0-SNAPSHOT.jar"]