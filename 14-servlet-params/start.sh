#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 14-servlet-params en http://localhost:8014"
mvn tomcat7:run -pl 14-servlet-params
