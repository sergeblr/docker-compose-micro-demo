# micro-orders runs under JVM
FROM anapsix/alpine-java
MAINTAINER sergeblr
COPY --from=cafemenu_mvn:latest /home/cafemenu/micro-orders/target/micro-orders-1.0.0-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "micro-orders-1.0.0-SNAPSHOT.jar"]