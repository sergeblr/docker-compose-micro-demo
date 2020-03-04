# micro-iteminorders runs under JVM
FROM anapsix/alpine-java
MAINTAINER sergeblr
COPY --from=cafemenu_mvn:latest /home/cafemenu/micro-iteminorders/target/micro-iteminorders-1.0.0-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "micro-iteminorders-1.0.0-SNAPSHOT.jar"]