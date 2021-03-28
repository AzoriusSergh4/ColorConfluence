FROM adoptopenjdk/openjdk11:alpine
COPY ./Backend/target/ColorConfluence-0.0.1-SNAPSHOT.jar /app/ColorConfluence-0.0.1-SNAPSHOT.jar
COPY ./start_connection.sh /app/start_connection.sh
EXPOSE 8080
CMD ["/app/start_connection.sh"]
