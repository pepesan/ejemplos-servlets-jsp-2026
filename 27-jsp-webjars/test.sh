#!/bin/bash
cd "$(dirname "$0")/.."
mvn test -pl 27-jsp-webjars
