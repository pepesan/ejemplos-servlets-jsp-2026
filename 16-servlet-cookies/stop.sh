#!/bin/bash
fuser -k 8016/tcp 2>/dev/null && echo "16-servlet-cookies detenido (puerto 8016)" || echo "Puerto 8016 no estaba en uso"
