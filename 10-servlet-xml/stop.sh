#!/bin/bash
fuser -k 8010/tcp 2>/dev/null && echo "10-servlet-xml detenido (puerto 8010)" || echo "Puerto 8010 no estaba en uso"
