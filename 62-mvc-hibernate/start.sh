#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 62-mvc-hibernate en http://localhost:8062"
# --add-opens necesario para Hibernate 3.6 + Javassist en Java 9+
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn tomcat7:run -pl 62-mvc-hibernate
