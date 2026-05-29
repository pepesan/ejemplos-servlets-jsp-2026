#!/bin/bash
fuser -k 8082/tcp 2>/dev/null && echo "02-servlet-lifecycle detenido (puerto 8082)" || echo "Puerto 8082 no estaba en uso"
