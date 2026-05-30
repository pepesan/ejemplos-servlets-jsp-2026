#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 17-servlet-sesiones en http://localhost:8017"
mvn tomcat7:run -pl 17-servlet-sesiones
