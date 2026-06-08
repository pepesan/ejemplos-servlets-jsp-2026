#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 27-jsp-webjars en http://localhost:8027"
mvn tomcat7:run -pl 27-jsp-webjars
