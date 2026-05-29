#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 03-jsp-jstl en http://localhost:8083"
mvn tomcat7:run -pl 20-jsp-jstl
