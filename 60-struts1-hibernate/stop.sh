#!/bin/bash
fuser -k 8086/tcp 2>/dev/null && echo "07-struts1-hibernate detenido (puerto 8086)" || echo "Puerto 8086 no estaba en uso"
