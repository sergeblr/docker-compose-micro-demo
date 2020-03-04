### Docker & docker-compose package & deploy automation  ```Microservices SpringBoot app example, Maven, MySQL, RabbitMQ ```

### Project description
 This is an example project, that shows how to automate maven-based multiservice project packaging & deployment.

### Prerequisites
- Docker v18.0, docker-compose v3.7

***
**Installing**  
 - Docker installing main tutorial: `https://docs.docker.com/install/`
 - Docker installing at Ubuntu: `https://docs.docker.com/install/linux/docker-ce/ubuntu/`

 - Download project from github
 URL: `https://github.com/sergeblr/docker-compose-micro-demo.git`
 - Run at root of unpacked project /cafemenu folder to build project:
 `docker-compose up --build`
 Wait until necessary dependencies will be downloaded, project packaged and services will be started.
 After docker-compose services 
 
**Running app**
 - Using plugin jetty:
   - inside /cafemenu/rest-app dir run: `mvn jetty:run`, then open in browser: `http://localhost:8082` for REST API with examples
   - inside /cafemenu/web-app dir run: `mvn jetty:run`, then open in browser: `http://localhost:8083` for WEB startpage
 
 - Using server tomcat9:
   - Install, configure & run tomcat 9 server: `https://www.howtoforge.com/tutorial/ubuntu-apache-tomcat/`
   - deploy *.WARs via tomcat web-interface to server
   - open CafeMenu web-app via browser at address as WARs named

**Running the tests**  
 `mvn clean test`

**Reports**  
`mvn site`

---

**External server**
 For testing ability (based on buddy.works autodeploy to own server):
   - rest-app `http://***.***.***.71:8082`
   - web-app `http://***.***.***.71:8083`

***

