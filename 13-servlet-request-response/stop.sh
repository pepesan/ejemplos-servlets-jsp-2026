#!/bin/bash
fuser -k 8013/tcp 2>/dev/null && echo "13-servlet-request-response detenido (puerto 8013)" || echo "Puerto 8013 no estaba en uso"
