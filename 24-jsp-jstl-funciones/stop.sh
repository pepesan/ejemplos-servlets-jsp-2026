#!/bin/bash
fuser -k 8024/tcp 2>/dev/null && echo "24-jsp-jstl-funciones detenido" || echo "Puerto 8024 libre"
