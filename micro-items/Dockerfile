# micro-items runs under JVM
FROM anapsix/alpine-java
MAINTAINER sergeblr
COPY --from=cafemenu_mvn:latest /home/cafemenu/micro-items/target/micro-items-1.0.0-SNAPSHOT.jar /
ENTRYPOINT ["java", "-jar", "micro-items-1.0.0-SNAPSHOT.jar"]



# OLD VER:
#FROM cafemenu_mvn:latest as cmbuild       # 1 stage
#CMD [""]                                  <--- compile & paclage here
#
#FROM anapsix/alpine-java                  # 2 stage
#MAINTAINER sergeblr
#COPY --from=cmbuild /home/cafemenu/micro-items/target/micro-items-1.0.0-SNAPSHOT.jar /    <--- copy from packaged image
#ENTRYPOINT ["java", "-jar", "micro-items-1.0.0-SNAPSHOT.jar"]