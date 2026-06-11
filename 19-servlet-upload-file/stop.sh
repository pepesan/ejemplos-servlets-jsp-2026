#!/bin/bash
fuser -k 8019/tcp 2>/dev/null && echo "19-servlet-upload-file detenido (puerto 8019)" || echo "Puerto 8019 no estaba en uso"
