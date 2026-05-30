#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 15-servlet-filtros en http://localhost:8015"
mvn tomcat7:run -pl 15-servlet-filtros
