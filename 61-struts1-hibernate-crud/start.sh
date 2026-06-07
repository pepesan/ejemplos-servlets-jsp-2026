#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 61-struts1-hibernate-crud en http://localhost:8061"
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn tomcat7:run -pl 61-struts1-hibernate-crud
