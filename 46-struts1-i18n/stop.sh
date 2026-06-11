#!/bin/bash
fuser -k 8046/tcp 2>/dev/null && echo "46-struts1-i18n detenido (puerto 8046)" || echo "Puerto 8046 no estaba en uso"
