#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 18-servlet-patrones en http://localhost:8018"
mvn tomcat7:run -pl 18-servlet-patrones
