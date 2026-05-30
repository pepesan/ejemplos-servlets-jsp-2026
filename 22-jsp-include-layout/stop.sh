#!/bin/bash
fuser -k 8022/tcp 2>/dev/null && echo "22-jsp-include-layout detenido" || echo "Puerto 8022 libre"
