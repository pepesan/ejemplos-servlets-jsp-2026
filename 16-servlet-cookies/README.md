# 16 — Servlet: Cookies HTTP

Ciclo de vida completo de cookies: crear, leer, modificar atributos y borrar.
Ejemplo práctico: preferencia de tema visual persistente 30 días.

## Servlets

| Servlet | URL | Función |
|---------|-----|---------|
| `CookieServlet` | `/cookies` | Listar, crear (sesión/persistente/segura) y borrar cookies |
| `TemaServlet` | `/tema` | Preferencia de tema (oscuro/claro/sistema) con cookie persistente |

## Conceptos clave

```java
// Crear y enviar una cookie
Cookie c = new Cookie("nombre", "valor");
c.setMaxAge(86400);       // segundos: -1=sesión, 0=borrar, >0=persistente
c.setPath("/");           // ámbito de la cookie
c.setHttpOnly(true);      // no accesible desde JavaScript
c.setSecure(true);        // solo HTTPS
resp.addCookie(c);

// Leer cookies de la petición
Cookie[] cookies = req.getCookies();  // null si no hay ninguna
for (Cookie c : cookies) {
    c.getName();   c.getValue();   c.getMaxAge();
}

// Borrar cookie: Max-Age=0
Cookie borrar = new Cookie("nombre", "");
borrar.setMaxAge(0);
borrar.setPath("/");
resp.addCookie(borrar);
```

## Arrancar

```bash
./start.sh        # http://localhost:8016
./stop.sh
./build.sh
./test.sh
```

## Pruebas curl

```bash
# Portada
curl -s http://localhost:8016/ | grep -o 'Cookies HTTP'

# Sin cookies: mensaje de vacío
curl -s http://localhost:8016/cookies | grep -o 'No hay cookies'

# Crear cookie de sesión → 302 redirect
curl -s -o /dev/null -w "%{http_code}" http://localhost:8016/cookies?accion=session

# Cookie aparece en la siguiente petición
curl -s -c /tmp/c16.txt http://localhost:8016/cookies?accion=session
curl -s -b /tmp/c16.txt http://localhost:8016/cookies | grep -o 'demo-session'

# Borrar cookie → 302
curl -s -b /tmp/c16.txt -o /dev/null -w "%{http_code}" \
  "http://localhost:8016/cookies?accion=borrar&nombre=demo-session"

# Tema por defecto: oscuro
curl -s http://localhost:8016/tema | grep -o 'oscuro' | head -1

# Cambiar tema a claro → 302
curl -s -c /tmp/c16t.txt -X POST http://localhost:8016/tema \
  --data "tema=claro" -o /dev/null -w "%{http_code}"

# Tema persiste en siguiente petición
curl -s -b /tmp/c16t.txt http://localhost:8016/tema | grep -o 'claro' | head -1
```
