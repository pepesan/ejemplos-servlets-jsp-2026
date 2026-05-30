#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 25-jsp-errores en http://localhost:8025"
mvn tomcat7:run -pl 25-jsp-errores
