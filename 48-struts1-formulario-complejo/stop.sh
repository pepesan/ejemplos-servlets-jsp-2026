#!/bin/bash
fuser -k 8048/tcp 2>/dev/null && echo "Puerto 8048 liberado" || echo "No había proceso en 8048"
