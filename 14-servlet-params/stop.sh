#!/bin/bash
fuser -k 8014/tcp 2>/dev/null && echo "14-servlet-params detenido (puerto 8014)" || echo "Puerto 8014 no estaba en uso"
