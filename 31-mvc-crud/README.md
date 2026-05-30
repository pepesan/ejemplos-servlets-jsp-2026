# 31 — MVC CRUD: Agenda de contactos

Extiende el patrón Front Controller de [30-mvc](../30-mvc/README.md) con CRUD completo:
editar, eliminar, filtrar la lista y una vista JSP reutilizable para crear y editar.

## Conceptos nuevos respecto a 30-mvc

| Concepto | Descripción |
|----------|-------------|
| **Editar** | `EditarContactoComando` pre-rellena `formulario.jsp` con datos existentes |
| **Eliminar** | `EliminarContactoComando` solo acepta POST; un GET devuelve 405 |
| **Filtrar** | `ListarContactosComando` acepta `?categoria=` y filtra en repositorio |
| **JSP reutilizable** | `formulario.jsp` detecta `${not empty contacto}` → modo editar vs. crear |
| **Validador extraído** | `ContactoValidator` con métodos `static` compartidos por Crear y Editar |
| **Campo enum** | `categoria` (AMIGO / TRABAJO / FAMILIA) como `<select>` en formulario |

## Rutas

| Método | URL | Comando | Resultado |
|--------|-----|---------|-----------|
| GET | `/app/contactos` | `ListarContactosComando` | `lista.jsp` |
| GET | `/app/contactos?categoria=X` | `ListarContactosComando` | `lista.jsp` filtrada |
| GET | `/app/contactos/ver?id=X` | `VerContactoComando` | `detalle.jsp` |
| GET | `/app/contactos/nuevo` | `CrearContactoComando` | `formulario.jsp` (vacío) |
| POST | `/app/contactos/nuevo` | `CrearContactoComando` | redirect PRG → `/app/contactos` |
| GET | `/app/contactos/editar?id=X` | `EditarContactoComando` | `formulario.jsp` (pre-relleno) |
| POST | `/app/contactos/editar?id=X` | `EditarContactoComando` | redirect PRG → `/app/contactos` |
| POST | `/app/contactos/eliminar` | `EliminarContactoComando` | redirect PRG → `/app/contactos` |

## Clases Java

| Clase | Rol |
|-------|-----|
| `FrontControllerServlet` | Mapea `pathInfo` al `Comando` registrado en `init()` |
| `Comando` | Interfaz: `String ejecutar(req, resp)` — devuelve vista o `null` si redirigió |
| `Contacto` | JavaBean: `id`, `nombre`, `email`, `telefono`, `categoria` (constantes AMIGO/TRABAJO/FAMILIA) |
| `ContactoRepositorio` | Lista estática con 5 contactos iniciales; `guardar`, `actualizar`, `eliminar`, `filtrarPorCategoria`, `reset()` |
| `ContactoValidator` | Métodos `static validar*(String)` y `validar(...)` → `Map<String,String>` errores |
| `ListarContactosComando` | Filtra por `?categoria=` si está presente |
| `VerContactoComando` | Lee `?id`, 404 si no existe |
| `CrearContactoComando` | GET → form vacío; POST → valida + guarda + PRG |
| `EditarContactoComando` | GET → form pre-relleno; POST → valida + actualiza + PRG |
| `EliminarContactoComando` | Solo POST: elimina + PRG; GET → 405 |

## Clave: formulario.jsp reutilizable

El mismo JSP sirve para crear y editar. El comando `EditarContactoComando` deposita el contacto existente en el request; `CrearContactoComando` no lo deposita. La vista se adapta:

```jsp
<c:set var="esEdicion" value="${not empty contacto}"/>

<%-- Acción del form según el modo --%>
<c:choose>
    <c:when test="${esEdicion}">
        <c:set var="accion" value="${pageContext.request.contextPath}/app/contactos/editar?id=${contacto.id}"/>
    </c:when>
    <c:otherwise>
        <c:set var="accion" value="${pageContext.request.contextPath}/app/contactos/nuevo"/>
    </c:otherwise>
</c:choose>

<%-- Pre-relleno: si hay error de validación usa el valor enviado; si no, el del contacto --%>
<input name="nombre" value="<c:out value='${not empty nombre ? nombre : contacto.nombre}'/>">
```

## Validaciones

| Campo | Regla |
|-------|-------|
| nombre | obligatorio, mínimo 2 caracteres |
| email | obligatorio, debe contener @ |
| telefono | **opcional** — si se rellena: solo dígitos, espacios y guiones, 7-15 caracteres |
| categoria | obligatoria, debe ser AMIGO, TRABAJO o FAMILIA |

## Tests

```bash
./test.sh   # mvn test → 38 tests, 0 fallos
```

| Test | Casos |
|------|-------|
| `ContactoTest` | Constructor, getters, constantes de categoría |
| `ContactoRepositorioTest` | `@Before reset()`; guardar, listar, buscar, actualizar, eliminar, filtrar, borrar inexistente |
| `ContactoValidatorTest` | `validarNombre/Email/Telefono/Categoria` — null, vacío, inválido, válido; validar completo |

## Arrancar

```bash
./start.sh   # Tomcat embebido → http://localhost:8031
./stop.sh    # para el proceso en el puerto 8031
./build.sh   # mvn clean package
```

## Pruebas curl

Con el servidor arrancado (`./start.sh`):

```bash
# Portada
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8031/
# Esperado: 200

# /app/ redirige a la lista
curl -s -o /dev/null -w "%{http_code} → %{redirect_url}\n" http://localhost:8031/app/
# Esperado: 302 → http://localhost:8031/app/contactos

# Lista completa
curl -s http://localhost:8031/app/contactos | grep -o 'Ana García'
# Esperado: Ana García

# Filtro por categoría
curl -s "http://localhost:8031/app/contactos?categoria=AMIGO" | grep -o 'AMIGO'
# Esperado: AMIGO

# Detalle existente
curl -s -o /dev/null -w "%{http_code}\n" "http://localhost:8031/app/contactos/ver?id=1"
# Esperado: 200

# Detalle inexistente → 404
curl -s -o /dev/null -w "%{http_code}\n" "http://localhost:8031/app/contactos/ver?id=999"
# Esperado: 404

# Formulario de creación (GET)
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8031/app/contactos/nuevo
# Esperado: 200

# Crear contacto válido → PRG (302)
curl -s -o /dev/null -w "%{http_code} → %{redirect_url}\n" \
  -X POST http://localhost:8031/app/contactos/nuevo \
  -d "nombre=Ana&email=ana@test.com&telefono=600111222&categoria=AMIGO"
# Esperado: 302 → http://localhost:8031/app/contactos

# Crear con errores → 200 con mensajes
curl -s -X POST http://localhost:8031/app/contactos/nuevo \
  -d "nombre=&email=noemail&telefono=&categoria=" | grep -o 'obligatorio'
# Esperado: obligatorio

# Formulario de edición pre-relleno
curl -s "http://localhost:8031/app/contactos/editar?id=1" | grep -o 'Ana'
# Esperado: Ana (nombre pre-relleno)

# Editar contacto válido → PRG (302)
curl -s -o /dev/null -w "%{http_code} → %{redirect_url}\n" \
  -X POST "http://localhost:8031/app/contactos/editar?id=1" \
  -d "nombre=Ana Actualizada&email=ana@new.com&telefono=&categoria=TRABAJO"
# Esperado: 302 → http://localhost:8031/app/contactos

# Eliminar contacto → PRG (302)
curl -s -o /dev/null -w "%{http_code} → %{redirect_url}\n" \
  -X POST http://localhost:8031/app/contactos/eliminar -d "id=3"
# Esperado: 302 → http://localhost:8031/app/contactos

# GET a /eliminar → 405 (solo se permite POST)
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8031/app/contactos/eliminar
# Esperado: 405

# Ruta desconocida → 404
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8031/app/desconocido
# Esperado: 404
```
