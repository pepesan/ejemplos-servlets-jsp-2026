#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 46-struts1-i18n..."
mvn clean package -pl 46-struts1-i18n
