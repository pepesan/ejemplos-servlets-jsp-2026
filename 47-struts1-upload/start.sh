#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 47-struts1-upload en http://localhost:8047"
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn tomcat7:run -pl 47-struts1-upload
