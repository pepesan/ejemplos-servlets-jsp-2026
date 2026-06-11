#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 46-struts1-i18n..."
mvn test -pl 46-struts1-i18n
