#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 41-struts1-crud en http://localhost:8041"
mvn tomcat7:run -pl 41-struts1-crud
