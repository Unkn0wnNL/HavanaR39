FROM openjdk:11
COPY ./build/libs/Havana-Server-all.jar /usr/src/app/Havana-Server-all.jar
WORKDIR /usr/src/app
EXPOSE 12322 12321 12309
ENV TZ="Europe/Copenhagen"
ENTRYPOINT ["java", "-Xmx500m", "-Xms300m", "-jar", "Havana-Server-all.jar"]