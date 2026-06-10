#!/bin/bash
cd "$(dirname "$0")/.."
mvn test -pl 62-mvc-hibernate
