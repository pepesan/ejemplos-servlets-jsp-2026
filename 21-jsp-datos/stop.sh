#!/bin/bash
fuser -k 8021/tcp 2>/dev/null && echo "21-jsp-datos detenido (puerto 8021)" || echo "Puerto 8021 no estaba en uso"
