#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 21-jsp-datos en http://localhost:8021"
mvn tomcat7:run -pl 21-jsp-datos
