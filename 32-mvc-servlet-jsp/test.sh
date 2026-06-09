#!/bin/bash
cd "$(dirname "$0")/.."
mvn test -pl 32-mvc-servlet-jsp
