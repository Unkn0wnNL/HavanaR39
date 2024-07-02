FROM openjdk:11
COPY ./build/libs/Havana-Web-all.jar /usr/src/app/Havana-Web-all.jar
WORKDIR /usr/src/app
EXPOSE 12322 12321 12309
ENV TZ="Europe/Copenhagen"
ENTRYPOINT ["java", "-Xmx500m", "-Xms300m", "-jar", "Havana-Web-all.jar"]