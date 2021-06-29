FROM adoptopenjdk/openjdk15

ARG JAR_FILE=target/probability-diabete-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} probability.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/probability.jar"]
