#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 16-servlet-cookies en http://localhost:8016"
mvn tomcat7:run -pl 16-servlet-cookies
