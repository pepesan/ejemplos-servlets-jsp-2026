#!/bin/bash
cd "$(dirname "$0")/.."
mvn clean package -pl 32-mvc-servlet-jsp
