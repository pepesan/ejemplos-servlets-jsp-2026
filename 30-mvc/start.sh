#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 04-mvc en http://localhost:8084"
mvn tomcat7:run -pl 30-mvc
