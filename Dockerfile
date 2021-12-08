FROM openjdk:11
ADD wait-for-it.sh /opt
ADD start.sh /opt
ADD target/exchangerate-0.0.1-SNAPSHOT.jar /opt
WORKDIR /opt
RUN chmod +x wait-for-it.sh
RUN chmod +x start.sh
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "./start.sh"]