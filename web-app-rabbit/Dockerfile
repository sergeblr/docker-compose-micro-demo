# web-app-rabbit runs under JVM
FROM cafemenu_mvn:latest as cmbuild
CMD [""]

FROM anapsix/alpine-java
MAINTAINER sergeblr
COPY --from=cmbuild /home/cafemenu/web-app-rabbit/target/web-app-rabbit-1.0.0-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "web-app-rabbit-1.0.0-SNAPSHOT.jar"]