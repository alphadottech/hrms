FROM adoptopenjdk/openjdk11:jre-11.0.12_7-alpine
ENV TZ="Asia/Calcutta"
ARG JAR_FILE=./target/hrms-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /usr/app/hrms.jar
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","hrms.jar"]