#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 02-servlet-lifecycle en http://localhost:8082"
mvn tomcat7:run -pl 10-servlet-lifecycle
