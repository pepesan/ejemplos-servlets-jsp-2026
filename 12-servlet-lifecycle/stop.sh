#!/bin/bash
fuser -k 8012/tcp 2>/dev/null && echo "12-servlet-lifecycle detenido (puerto 8012)" || echo "Puerto 8012 no estaba en uso"
