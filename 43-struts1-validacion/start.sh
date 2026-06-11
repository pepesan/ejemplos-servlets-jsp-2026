#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 43-struts1-validacion en http://localhost:8043"
mvn tomcat7:run -pl 43-struts1-validacion
