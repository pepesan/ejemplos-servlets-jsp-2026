#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 44-struts1-dynaform en http://localhost:8044"
mvn tomcat7:run -pl 44-struts1-dynaform
