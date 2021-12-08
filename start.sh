#!/usr/bin/env bash

echo "Checking for database status ..."
./wait-for-it.sh mysql-standalone:3306 -t 15
java -jar exchangerate-0.0.1-SNAPSHOT.jar