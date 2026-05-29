#!/bin/bash
fuser -k 8083/tcp 2>/dev/null && echo "03-jsp-jstl detenido (puerto 8083)" || echo "Puerto 8083 no estaba en uso"
