# 30 — Patrón MVC Clásico (Front Controller)

Implementación mínima del patrón Model-View-Controller con un Servlet genérico como Front Controller.
Un único servlet recibe todas las peticiones bajo `/app/*`, las enruta al comando correspondiente
y hace forward a la vista JSP protegida en `WEB-INF/vistas/`.

## Arquitectura

```
Navegador
   │  GET /app/productos
   ▼
FrontControllerServlet   (mapeo /app/*)
   │  pathInfo = "/productos"  →  busca en mapa de comandos
   ▼
ListarProductosComando
   │  req.setAttribute("productos", ProductoRepositorio.listarTodos())
   │  return "lista.jsp"
   ▼
/WEB-INF/vistas/lista.jsp   (no accesible directamente desde el navegador)
```

## Rutas

| Método | URL | Comando | Vista |
|--------|-----|---------|-------|
| GET | `/app/` | — | redirect a `/app/productos` |
| GET | `/app/productos` | `ListarProductosComando` | `lista.jsp` |
| GET | `/app/productos/ver?id=X` | `VerProductoComando` | `detalle.jsp` |
| GET | `/app/productos/nuevo` | `CrearProductoComando` | `formulario.jsp` |
| POST | `/app/productos/nuevo` | `CrearProductoComando` | redirect (PRG) o `formulario.jsp` con errores |

## Clases Java

| Clase | Rol |
|-------|-----|
| `FrontControllerServlet` | Controlador frontal: mapea `pathInfo` al `Comando` registrado en `init()` |
| `Comando` | Interfaz: `String ejecutar(req, resp)` — devuelve nombre de vista o `null` si ya redirigió |
| `ListarProductosComando` | Deposita la lista en `request` y devuelve `"lista.jsp"` |
| `VerProductoComando` | Lee `?id`, busca en repositorio; 404 si no existe |
| `CrearProductoComando` | GET → formulario; POST → valida, guarda, redirect PRG |
| `Producto` | JavaBean: `id`, `nombre`, `precio` |
| `ProductoRepositorio` | Lista estática en memoria con 5 productos iniciales; `reset()` para tests |

## Cómo funciona el FrontControllerServlet

```java
@Override
public void init() {
    comandos = new HashMap<>();
    comandos.put("/productos",       new ListarProductosComando());
    comandos.put("/productos/ver",   new VerProductoComando());
    comandos.put("/productos/nuevo", new CrearProductoComando());
}

@Override
protected void service(HttpServletRequest req, HttpServletResponse resp) {
    String path = req.getPathInfo();  // ej: "/productos/ver"
    Comando cmd = comandos.get(path);
    String vista = cmd.ejecutar(req, resp);
    if (vista != null)
        req.getRequestDispatcher("/WEB-INF/vistas/" + vista).forward(req, resp);
    // null → el comando ya hizo sendRedirect (patrón PRG)
}
```

## Ciclo POST → PRG en CrearProductoComando

```
POST /app/productos/nuevo
   ├── errores de validación  → forward a formulario.jsp (campos re-rellenos + errores inline)
   └── datos válidos          → ProductoRepositorio.guardar() + sendRedirect /app/productos
```

## Validaciones de CrearProductoComando

| Campo | Regla |
|-------|-------|
| nombre | obligatorio, mínimo 2 caracteres |
| precio | obligatorio, número mayor que 0 |

## Tests

Los tests son unitarios puros sin contenedor, siguiendo el mismo patrón que el resto de módulos:
los métodos de negocio son `static` y se invocan directamente.

| Test | Qué verifica |
|------|-------------|
| `ProductoTest` | Constructor, getters/setters, `toString` |
| `ProductoRepositorioTest` | `guardar`, `listarTodos`, `buscarPorId`, lista inmutable; `@Before reset()` |
| `CrearProductoComandoTest` | `validarNombre` y `validarPrecio` — casos null, vacío, inválido, válido |

```bash
./test.sh   # mvn test → 22 tests, 0 fallos
```

## Arrancar

```bash
./start.sh   # Tomcat embebido → http://localhost:8084
./stop.sh    # para el proceso en el puerto 8084
./build.sh   # mvn clean package
```

## Pruebas curl

Con el servidor arrancado (`./start.sh`):

```bash
# Portada (index.html, servida directamente por Tomcat)
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8084/
# Esperado: 200

# /app/ redirige al catálogo
curl -s -o /dev/null -w "%{http_code} → %{redirect_url}\n" http://localhost:8084/app/
# Esperado: 302 → http://localhost:8084/app/productos

# Lista de productos (tabla HTML)
curl -s http://localhost:8084/app/productos | grep -o '<td>[^<]*</td>'
# Esperado: celdas con Teclado mecánico, Ratón inalámbrico, etc.

# Detalle de producto existente
curl -s -o /dev/null -w "%{http_code}\n" "http://localhost:8084/app/productos/ver?id=1"
# Esperado: 200

# Detalle de producto inexistente → 404
curl -s -o /dev/null -w "%{http_code}\n" "http://localhost:8084/app/productos/ver?id=999"
# Esperado: 404

# Formulario de alta (GET)
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8084/app/productos/nuevo
# Esperado: 200

# Alta válida → PRG (302 a /app/productos)
curl -s -o /dev/null -w "%{http_code} → %{redirect_url}\n" \
  -X POST http://localhost:8084/app/productos/nuevo \
  -d "nombre=Auriculares&precio=49.99"
# Esperado: 302 → http://localhost:8084/app/productos

# Alta inválida → 200 con formulario y mensajes de error
curl -s -X POST http://localhost:8084/app/productos/nuevo \
  -d "nombre=&precio=abc" | grep -o 'obligatorio'
# Esperado: obligatorio

curl -s -X POST http://localhost:8084/app/productos/nuevo \
  -d "nombre=&precio=abc" | grep -o 'número válido'
# Esperado: número válido

# Ruta desconocida → 404
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8084/app/desconocido
# Esperado: 404
```
