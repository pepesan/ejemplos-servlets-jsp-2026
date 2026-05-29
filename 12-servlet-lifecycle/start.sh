#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 12-servlet-lifecycle en http://localhost:8012"
mvn tomcat7:run -pl 12-servlet-lifecycle
