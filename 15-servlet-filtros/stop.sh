#!/bin/bash
fuser -k 8015/tcp 2>/dev/null && echo "15-servlet-filtros detenido (puerto 8015)" || echo "Puerto 8015 no estaba en uso"
