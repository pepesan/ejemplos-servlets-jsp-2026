#!/bin/bash
cd "$(dirname "$0")/.."
echo "Arrancando 46-struts1-i18n en http://localhost:8046"
mvn tomcat7:run -pl 46-struts1-i18n
