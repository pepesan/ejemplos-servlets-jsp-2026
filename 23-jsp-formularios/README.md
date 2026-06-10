# 23 — JSP: Formularios con validación y PRG

Ciclo completo de formularios web: alta con campos vacíos y edición con datos pre-cargados. Ambos usan validación servidor, errores inline y patrón PRG.

## Formulario de registro (`/registro`)

```
GET  /registro          → formulario vacío (RegistroBean vacío en request)
POST /registro          → valida todos los campos
  ├── errores           → forward a formulario.jsp (errores + bean en request)
  └── sin errores       → session.setAttribute + sendRedirect /registro?ok=1  (PRG)
GET  /registro?ok=1     → exito.jsp (lee nombre de sesión, lo consume)
```

| Campo | Regla |
|-------|-------|
| nombre | obligatorio, mínimo 3 caracteres |
| email | obligatorio, debe contener @ |
| password | obligatoria, mínimo 6 caracteres |
| edad | obligatoria, número entero entre 18 y 120 |

## Formulario de edición (`/editar?id=X`)

Pre-carga un perfil existente de una "BD" en memoria (3 usuarios de demo) y pre-rellena todos los campos del formulario.

```
GET  /editar?id=X       → carga PerfilBean de la BD y hace forward a editar.jsp
POST /editar?id=X       → valida; si errores: forward con errores y valores enviados
                          si ok: BD.put(id, editado) + sendRedirect /editar?id=X&ok=1  (PRG)
GET  /editar?id=X&ok=1  → editar.jsp con banner de éxito
```

| Campo | Regla |
|-------|-------|
| nombre | obligatorio, mínimo 3 caracteres |
| email | obligatorio, debe contener @ |
| telefono | opcional; si se rellena, exactamente 9 dígitos |
| ciudad | obligatoria |
| rol | select: usuario / editor / admin |
| activo | checkbox boolean |

### Claves del pre-relleno en `editar.jsp`

```jsp
<%-- Input de texto: value con c:out para escapar HTML --%>
<input type="text" name="nombre" value="<c:out value='${bean.nombre}'/>">

<%-- Select: opción activa mediante EL ternario --%>
<option value="admin" ${bean.rol eq 'admin' ? 'selected' : ''}>Administrador</option>

<%-- Checkbox: checked mediante EL sobre boolean --%>
<input type="checkbox" name="activo" value="true" ${bean.activo ? 'checked' : ''}>
```

> El checkbox no envía parámetro cuando está desmarcado; el servlet usa `"true".equals(req.getParameter("activo"))`.

## Diseño común en las JSPs

- `<c:out value='${bean.campo}'/>` en `value=""` — pre-rellena y escapa HTML
- `${errores.campo}` con `<c:if test="${not empty errores.campo}">` — error inline
- `${errores.size()}` — resumen de errores en el banner superior

## Arrancar

```bash
./start.sh        # http://localhost:8023
```

## Pruebas curl

```bash
# ── Registro ──────────────────────────────────────────────────────────────────

# GET: formulario vacío
curl -s http://localhost:8023/registro | grep -o 'Formulario de registro'

# POST vacío: 4 errores
curl -s -X POST http://localhost:8023/registro --data "" | grep -o 'obligatori' | wc -l   # 4

# POST email sin @
curl -s -X POST http://localhost:8023/registro \
  --data "nombre=Ana&email=noemail&password=secret123&edad=25" | grep -o 'debe contener @'

# POST válido → 302
curl -s -X POST http://localhost:8023/registro \
  --data "nombre=Carlos&email=carlos@test.com&password=secret123&edad=30" \
  -o /dev/null -w "%{http_code}"   # 302

# Nombre se conserva en re-render con errores
curl -s -X POST http://localhost:8023/registro \
  --data "nombre=Elena&email=malemail&password=short&edad=abc" | grep -o 'Elena'

# ── Edición ───────────────────────────────────────────────────────────────────

# GET: carga perfil de Ana (id=1) con datos pre-rellenos
curl -s "http://localhost:8023/editar?id=1" | grep -o 'Ana'

# GET: carga perfil de Carmen (id=3, rol=usuario, inactivo)
curl -s "http://localhost:8023/editar?id=3" | grep -o 'Carmen'

# El rol del usuario 3 viene pre-seleccionado como "usuario"
curl -s "http://localhost:8023/editar?id=3" | grep -o 'value="usuario" selected'

# POST con nombre vacío → error inline
curl -s -X POST "http://localhost:8023/editar?id=1" \
  --data "nombre=&email=ana@ejemplo.com&ciudad=Madrid&rol=admin" | grep -o 'obligatorio'

# POST con teléfono inválido → error inline
curl -s -X POST "http://localhost:8023/editar?id=1" \
  --data "nombre=Ana&email=ana@ejemplo.com&telefono=123&ciudad=Madrid&rol=admin" \
  | grep -o '9 dígitos'

# POST válido → 302
curl -s -X POST "http://localhost:8023/editar?id=1" \
  --data "nombre=Ana+García&email=ana@ejemplo.com&telefono=612345678&ciudad=Madrid&rol=admin&activo=true" \
  -o /dev/null -w "%{http_code}"   # 302

# GET ?ok=1 → banner de éxito
curl -s "http://localhost:8023/editar?id=1&ok=1" | grep -o 'Cambios guardados'
```
