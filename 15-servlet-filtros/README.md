# 15 — Servlet: Filtros

Módulo que implementa los tres filtros clásicos de una aplicación Java EE:
log de peticiones, charset global y autenticación por sesión.

## Filtros

| Filtro | Mapeo | Función |
|--------|-------|---------|
| `CharsetFilter` | `/*` | Fija UTF-8 en petición y respuesta antes del primer acceso |
| `LoggingFilter` | `/*` | Registra método HTTP, URI y tiempo de respuesta en stdout |
| `AuthFilter` | `/protegido/*` | Verifica `session.getAttribute("usuario")`; redirige a `/login` si falta |

## Servlets

| Servlet | URL | Descripción |
|---------|-----|-------------|
| `LoginServlet` | `/login` | GET=formulario, POST=autentica (admin/admin) |
| `ProtegidoServlet` | `/protegido/area` | Área accesible solo con sesión activa |
| `LogoutServlet` | `/logout` | Invalida la sesión y redirige a `/` |

## Cadena de filtros

```
Petición → CharsetFilter → LoggingFilter → AuthFilter → Servlet
                                                ↓ (si no autenticado)
                                          sendRedirect(/login)
```

El **orden** de los `<filter-mapping>` en `web.xml` determina el orden de ejecución.

## Cómo funciona AuthFilter

```java
// Extrae la lógica de decisión a métodos estáticos (testables sin contenedor)
static boolean necesitaAutenticacion(String uri) {
    return uri != null && uri.contains("/protegido");
}

static boolean estaAutenticado(HttpSession sesion) {
    return sesion != null && sesion.getAttribute("usuario") != null;
}
```

## Arrancar

```bash
./start.sh        # http://localhost:8015
./stop.sh
./build.sh
./test.sh
```

## Pruebas curl

```bash
# Página de inicio (pasa por CharsetFilter + LoggingFilter)
curl -s http://localhost:8015/ | grep -o 'Filtros de Servlet'

# Zona protegida sin sesión → 302 redirect a /login
curl -s -o /dev/null -w "%{http_code}" http://localhost:8015/protegido/area
# Esperado: 302

# Verificar que redirige a /login
curl -s -o /dev/null -w "%{redirect_url}" http://localhost:8015/protegido/area
# Esperado: http://localhost:8015/login

# Página de login → muestra el formulario
curl -s http://localhost:8015/login | grep -o 'Acceso'

# Login correcto → redirige a /protegido/area (guarda cookie de sesión)
curl -s -c /tmp/cookies15.txt -X POST http://localhost:8015/login \
  --data "usuario=admin&clave=admin" \
  -o /dev/null -w "%{http_code}"
# Esperado: 302

# Acceso con sesión → área protegida visible
curl -s -b /tmp/cookies15.txt http://localhost:8015/protegido/area \
  | grep -o 'protegida'

# Datos de sesión: usuario admin visible en el área protegida
curl -s -b /tmp/cookies15.txt http://localhost:8015/protegido/area \
  | grep -o 'admin'

# Login incorrecto → vuelve al formulario con error
curl -s -X POST http://localhost:8015/login \
  --data "usuario=root&clave=wrong" \
  | grep -o 'incorrectas'

# Logout → invalida sesión (302 a /)
curl -s -c /tmp/cookies15.txt -b /tmp/cookies15.txt \
  -o /dev/null -w "%{http_code}" http://localhost:8015/logout
# Esperado: 302

# Tras logout: /protegido/area vuelve a redirigir a /login
curl -s -b /tmp/cookies15.txt \
  -o /dev/null -w "%{http_code}" http://localhost:8015/protegido/area
# Esperado: 302
```

## Flujo de demostración

1. Accede a `/protegido/area` → `AuthFilter` redirige a `/login`.
2. Observa en la consola las entradas de `LoggingFilter`.
3. Login con `admin` / `admin` → accede al área protegida.
4. Logout → la sesión se invalida → intenta acceder de nuevo a `/protegido/area`.
