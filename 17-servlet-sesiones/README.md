# 17 — Servlet: Sesiones HTTP

`HttpSession` para mantener estado de usuario entre peticiones.
Tres ejemplos: inspección del ciclo de vida, contador por usuario y carrito de compra.

## Servlets

| Servlet | URL | Función |
|---------|-----|---------|
| `SesionServlet` | `/sesion` | ID, fechas, timeout, atributos; crear e invalidar sesión |
| `ContadorServlet` | `/contador` | Contador de visitas por usuario en sesión |
| `CarritoServlet` | `/carrito` | Lista de artículos almacenada en sesión |

## Conceptos clave

```java
// Obtener sesión
HttpSession s = req.getSession(true);   // crea si no existe
HttpSession s = req.getSession(false);  // null si no existe

// Atributos
s.setAttribute("clave", objeto);
Object v = s.getAttribute("clave");
s.removeAttribute("clave");

// Información
s.getId();                     // JSESSIONID
s.isNew();                     // primera petición
s.getCreationTime();           // milisegundos
s.getMaxInactiveInterval();    // timeout en segundos (web.xml: <session-timeout>30</session-timeout>)

// Destruir
s.invalidate();
```

## Sesión vs Cookie

| | Cookie | HttpSession |
|--|--------|-------------|
| Dónde vive | Navegador | Servidor |
| Tamaño | ~4 KB | Sin límite |
| Seguridad | El cliente puede leerla | El cliente solo ve el ID |
| Persistencia | Max-Age configurable | Hasta invalidate() o timeout |

## Arrancar

```bash
./start.sh        # http://localhost:8017
./stop.sh
./build.sh
./test.sh
```

## Pruebas curl

```bash
# Sin sesión activa
curl -s http://localhost:8017/sesion | grep -o 'No hay sesión'

# Crear sesión → 302, guarda cookie JSESSIONID
curl -s -c /tmp/c17.txt http://localhost:8017/sesion?accion=crear \
  -o /dev/null -w "%{http_code}"

# Con sesión: muestra atributos
curl -s -b /tmp/c17.txt http://localhost:8017/sesion | grep -o 'Atributo de prueba'

# Contador: primera visita → 1
curl -s -c /tmp/ctr.txt http://localhost:8017/contador | grep -o '>1<'

# Contador: segunda visita → 2
curl -s -b /tmp/ctr.txt -c /tmp/ctr.txt http://localhost:8017/contador | grep -o '>2<'

# Carrito vacío
curl -s -c /tmp/carr.txt http://localhost:8017/carrito | grep -o 'carrito está vacío'

# Agregar artículo → 302
curl -s -b /tmp/carr.txt -c /tmp/carr.txt \
  "http://localhost:8017/carrito?accion=agregar&item=Teclado" \
  -o /dev/null -w "%{http_code}"

# Artículo persiste en siguiente petición
curl -s -b /tmp/carr.txt http://localhost:8017/carrito | grep -o 'Teclado'

# Vaciar → 302
curl -s -b /tmp/carr.txt -c /tmp/carr.txt \
  "http://localhost:8017/carrito?accion=vaciar" \
  -o /dev/null -w "%{http_code}"
```
