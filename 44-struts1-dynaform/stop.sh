#!/bin/bash
fuser -k 8044/tcp 2>/dev/null && echo "44-struts1-dynaform detenido (puerto 8044)" || echo "Puerto 8044 no estaba en uso"
