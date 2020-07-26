# MortgageApp-BusinessLayer

Steps for Deploying business-layer on Docker
1) first clean up the target folder with 
   mvn clean (this can also be done from the IDE by running Maven Clean) 
   
2) mvn install (this can also be done from IDE by running Maven Install).
   This command will create a “business-layer-docker.jar” in the target directory of the working directory.
   
3) Create Docker image use command [docker build -f Dockerfile -t business-layer-docker .]

4) View all running containers use command [docker ps]

5) Stop Running container use command [deocker stop <Container id>]

6) Run Docker image use command [docker run -p 9090:8080 -t business-layer-docker]

7) Push Docker image on DockerHub use command [$ docker push <username>/business-layer-docker:<version No.>]
