#!/bin/bash
fuser -k 8023/tcp 2>/dev/null && echo "23-jsp-formularios detenido" || echo "Puerto 8023 libre"
