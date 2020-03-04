# CafeMenu image file with CACHED dependencies
FROM maven:3.6.3-jdk-8-slim
MAINTAINER sergeblr
WORKDIR /home/cafemenu

# Copy all subModules POMs that declared in SuperPOMd
COPY ./model/pom.xml model/pom.xml
COPY ./test-db/pom.xml test-db/pom.xml
COPY ./dao/pom.xml dao/pom.xml
COPY ./dao-api/pom.xml dao-api/pom.xml
COPY ./service/pom.xml service/pom.xml
COPY ./service-api/pom.xml service-api/pom.xml
COPY ./micro-items/pom.xml micro-items/pom.xml
COPY ./micro-iteminorders/pom.xml micro-iteminorders/pom.xml
COPY ./micro-orders/pom.xml micro-orders/pom.xml
COPY ./web-app-rabbit/pom.xml web-app-rabbit/pom.xml

# COPY SuperPOM
COPY ./pom.xml pom.xml

# Fetch maven dependencies declared in SUPER pom.xml file (w/o project modules artifacts)
RUN mvn -f pom.xml de.qaware.maven:go-offline-maven-plugin:1.2.5:resolve-dependencies
# add '-N' to mvn -> submodules will be ignored (no need to copy all subPOMs), but modules dependencies will NOT be fetched
# add '-DexcludeGroupIds=com.epam.summer19' to mvn -> dependencies with that GroupId will be ignored
# DEPRECATED: '-Dmaven.repo.local=.m2/repository' to mvn -> set maven repository folder
# !!!!! ALL results TILL HERE will be cached by docker, until any pom.xml will be CHANGED !!!!!

# Build all project with previously fetched dependencies, packaged all sumbodules & services
COPY . .
RUN mvn -f pom.xml -B -DskipTests package
# DEPRECATED: '-Dmaven.repo.local=.m2/repository' to mvn -> set maven repository folder

CMD [""]
