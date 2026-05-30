#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 31-mvc-crud en http://localhost:8031"
mvn tomcat7:run -pl 31-mvc-crud
