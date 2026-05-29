#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 13-servlet-request-response en http://localhost:8013"
mvn tomcat7:run -pl 13-servlet-request-response
