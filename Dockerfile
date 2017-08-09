FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/newsback-api.jar /newsback-api/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/newsback-api/app.jar"]
