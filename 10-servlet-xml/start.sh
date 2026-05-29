#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 10-servlet-xml en http://localhost:8010"
mvn tomcat7:run -pl 10-servlet-xml
