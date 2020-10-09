#!/bin/sh
until nc -z -w50 database 3306
do
	echo "Waiting for database..."
	sleep 5
done
java -jar /app/ColorConfluence-0.0.1-SNAPSHOT.jar