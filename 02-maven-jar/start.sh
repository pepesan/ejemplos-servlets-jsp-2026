#!/bin/bash
cd "$(dirname "$0")/.."
mvn clean package -pl 02-maven-jar -q
echo "Ejecutando fat-JAR..."
java -jar 02-maven-jar/target/02-maven-jar-1.0-SNAPSHOT-jar-with-dependencies.jar
