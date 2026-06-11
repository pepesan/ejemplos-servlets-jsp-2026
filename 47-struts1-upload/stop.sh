#!/bin/bash
fuser -k 8047/tcp 2>/dev/null && echo "47-struts1-upload detenido (puerto 8047)" || echo "Puerto 8047 no estaba en uso"
