#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 05-struts1 en http://localhost:8085"
mvn tomcat7:run -pl 40-struts1
