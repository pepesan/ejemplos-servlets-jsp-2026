#!/bin/bash
fuser -k 8025/tcp 2>/dev/null && echo "25-jsp-errores detenido (puerto 8025)" || echo "Puerto 8025 no estaba en uso"
