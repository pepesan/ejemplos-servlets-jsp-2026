#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 60-struts1-hibernate en http://localhost:8086"
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn tomcat7:run -pl 60-struts1-hibernate
