#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 60-struts1-hibernate en http://localhost:8086"
mvn tomcat7:run -pl 60-struts1-hibernate
