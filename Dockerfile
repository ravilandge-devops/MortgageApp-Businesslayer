FROM openjdk:8
EXPOSE 9090
ADD target/business-layer-docker.jar business-layer-docker.jar
ENTRYPOINT ["java","-jar","/business-layer-docker.jar"]