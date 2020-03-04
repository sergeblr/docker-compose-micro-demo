### Docker & docker-compose package & deploy automation  ```Microservices SpringBoot app example, Maven, MySQL, RabbitMQ ```

### Project description
 This is an example project, that shows how to automate maven-based multiservice project packaging & deployment.

### Prerequisites
- Docker v18.0, Docker-compose v3.7

***
**Installing and running**  
 - Docker installing main tutorial: `https://docs.docker.com/install/` (Ubuntu: `https://docs.docker.com/install/linux/docker-ce/ubuntu/`)

 - Download project from github
 URL: `https://github.com/sergeblr/docker-compose-micro-demo.git`
 - Run at root of unpacked project folder to build project:  
 `docker-compose up --build`  
 Wait until necessary images and dependencies will be downloaded, project will be packaged and services started.  
 - After docker-compose services startup check URL:  
 `http://localhost:8090/`  
 
 **Note:**  
 - If any of pom.xml files will be changed, maven dependencies will be redownloaded and project will be rebuilded
 - If only sources will be changed, docker-cached dependencies will be used and project will be rebuilded
 
***
