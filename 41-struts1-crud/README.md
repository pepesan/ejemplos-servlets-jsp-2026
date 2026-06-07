# 41 — Struts 1.x: CRUD en memoria

CRUD completo de **Contactos** (crear, listar, editar, eliminar) usando cinco Actions
distintos sobre un repositorio en memoria. Amplía el ejemplo 40 mostrando cómo:

- Varios Actions comparten un único `ActionForm`.
- El patrón PRG (Post/Redirect/Get) se aplica con `redirect="true"` en los forwards.
- `EditarAction` carga datos del repositorio directamente en el form bean.

> ⚠️ **Java 8 obligatorio.** Struts 1.3.x usa reflexión que Java 9+ bloquea.
> `sdk install java 8.0.412-tem && sdk use java 8.0.412-tem`

## Estructura del módulo

```
Contacto              — entidad (id, nombre, email, telefono)
ContactoRepositorio   — almacén en memoria (LinkedHashMap + AtomicInteger)
ContactoForm          — ActionForm compartido por NuevoAction, EditarAction y GuardarAction
ListarAction          — carga la lista y hace forward a lista.jsp
VerAction             — carga un contacto por id y hace forward a detalle.jsp
NuevoAction           — inicializa un form vacío y hace forward a formulario.jsp
EditarAction          — carga el contacto en el form y hace forward a formulario.jsp
GuardarAction         — valida, crea o actualiza; redirect a /listar.do (PRG)
EliminarAction        — elimina; redirect a /listar.do (PRG)
```

## Flujo de cada operación

### Ver detalle

```
GET /ver.do?id=2
   ▼ VerAction.execute()
   │  request.getParameter("id")  →  "2"
   │  c = ContactoRepositorio.buscarPorId(2)
   │  request.setAttribute("contacto", c)
   │  findForward("detalle")  →  forward interno
   ▼
detalle.jsp   — muestra los campos con <c:out> + botones editar y eliminar
               (si el id no existe → redirect a /listar.do)
```

### Listar

```
GET /listar.do
   ▼ ActionServlet → ListarAction.execute()
   │  request.setAttribute("contactos", ContactoRepositorio.listar())
   │  findForward("lista")  →  forward interno
   ▼
lista.jsp   — itera ${contactos} con JSTL <c:forEach>
```

### Crear

```
GET /nuevo.do
   ▼ ActionServlet
   │  crea ContactoForm, llama reset() → form vacío
   ▼ NuevoAction.execute()
   │  findForward("formulario")  →  forward interno
   ▼
formulario.jsp   — título "Nuevo contacto", id oculto vacío

POST /guardar.do  nombre=Ana&email=ana@x.com&telefono=600...&id=
   ▼ ActionServlet → puebla ContactoForm por reflexión
   ▼ GuardarAction.execute()
   │  valida → OK
   │  id vacío → new Contacto() → ContactoRepositorio.guardar(c) → id asignado
   │  findForward("lista")  →  REDIRECT /listar.do  (PRG)
   ▼
GET /listar.do   — el navegador sigue la redirección
```

### Editar

```
GET /editar.do?id=3
   ▼ ActionServlet → crea ContactoForm, reset(), setId("3")
   ▼ EditarAction.execute()
   │  id = Integer.parseInt(contactoForm.getId())  →  3
   │  c = ContactoRepositorio.buscarPorId(3)
   │  contactoForm.setNombre(c.getNombre())   ← sobreescribe los campos
   │  contactoForm.setEmail(c.getEmail())
   │  contactoForm.setTelefono(c.getTelefono())
   │  findForward("formulario")  →  forward interno
   ▼
formulario.jsp   — título "Editar contacto #3", campos rellenos

POST /guardar.do  nombre=Ana2&email=ana2@x.com&id=3
   ▼ GuardarAction.execute()
   │  id = 3 → ContactoRepositorio.buscarPorId(3) → contacto existente
   │  c.setNombre("Ana2") …  → ContactoRepositorio.guardar(c)  (actualiza)
   │  findForward("lista")  →  REDIRECT /listar.do  (PRG)
```

### Eliminar

```
POST /eliminar.do  id=3
   ▼ EliminarAction.execute()
   │  request.getParameter("id")  →  "3"
   │  ContactoRepositorio.eliminar(3)
   │  findForward("lista")  →  REDIRECT /listar.do  (PRG)
```

### Validación con errores (sin redirect)

```
POST /guardar.do  nombre=  (vacío)
   ▼ GuardarAction.execute()
   │  validar() → ActionErrors con "error.nombre.requerido"
   │  saveErrors(request, errors)
   │  findForward("formulario")  →  FORWARD interno (no redirect)
   ▼
formulario.jsp
   │  <html:errors/> muestra "El nombre es obligatorio."
   │  <html:text> rellena los campos con los valores enviados
   │  el id oculto se preserva → al reenviar sigue siendo edición
```

## struts-config.xml: un form-bean, cinco actions

```xml
<form-beans>
    <form-bean name="contactoForm" type="com.cursosdedesarrollo.ContactoForm"/>
</form-beans>

<action path="/ver"      type="...VerAction">                   ← sin form
<action path="/listar"   type="...ListarAction">                ← sin form
<action path="/nuevo"    type="...NuevoAction"   name="contactoForm" ...>
<action path="/editar"   type="...EditarAction"  name="contactoForm" ...>
<action path="/guardar"  type="...GuardarAction" name="contactoForm" ...>
    <forward name="lista" path="/listar.do" redirect="true"/>   ← PRG
<action path="/eliminar" type="...EliminarAction">              ← sin form
    <forward name="lista" path="/listar.do" redirect="true"/>   ← PRG
```

`redirect="true"` en el forward hace que `ActionServlet` llame a
`response.sendRedirect()` en lugar de `RequestDispatcher.forward()`.
El navegador recibe un 302 y lanza un nuevo GET, rompiendo el ciclo
de reenvío al pulsar F5.

## Por qué un único ActionForm para crear y editar

`formulario.jsp` sirve para los dos casos. El campo `<html:hidden property="id"/>`:

- En **creación**: `id` está vacío; `GuardarAction` detecta `id == ""` → `new Contacto()`.
- En **edición**: `id` vale p. ej. `"3"`; `GuardarAction` carga el contacto existente y lo actualiza.

La vista no necesita saber si es un alta o una modificación: solo renderiza lo que hay en el form.

## Tests

```bash
./test.sh   # mvn test → 7 tests, 0 fallos
```

`ContactoRepositorioTest` prueba el repositorio sin contenedor:

| Test | Caso |
|------|------|
| `guardarAsignaIdAutomatico` | `id=0` → el repositorio asigna un id positivo |
| `buscarPorIdDevuelveElContactoGuardado` | guarda y recupera por id |
| `buscarPorIdInexistenteDevuelveNull` | id 999 → `null` |
| `guardarActualizaContactoExistente` | mismo id → sobreescribe; sigue habiendo 1 elemento |
| `eliminarBorraElContacto` | elimina → `buscarPorId` devuelve `null` |
| `listarDevuelveTodosLosContactos` | 3 elementos guardados → lista de tamaño 3 |
| `limpiarVaciaElRepositorio` | `limpiar()` → lista vacía |

## Arrancar

```bash
./start.sh   # Tomcat embebido → http://localhost:8041
./stop.sh    # mata el proceso en el puerto 8041
./build.sh   # mvn clean package
```

## Curl tests

```bash
# Ver detalle de contacto id=2
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8041/ver.do?id=2
# Esperado: 200

curl -s http://localhost:8041/ver.do?id=2 | grep -o "Luis"
# Esperado: Luis

# Ver id inexistente → redirect a la lista
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8041/ver.do?id=999
# Esperado: 302
```

## Pruebas curl

Con el servidor arrancado (`./start.sh`):

```bash
# Portada
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8041/
# Esperado: 200

# Listar contactos (datos iniciales)
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8041/listar.do
# Esperado: 200

curl -s http://localhost:8041/listar.do | grep -o "Ana García"
# Esperado: Ana García

# Formulario de nuevo contacto
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8041/nuevo.do
# Esperado: 200

# Crear contacto válido → 302 redirect a /listar.do
curl -s -o /dev/null -w "%{http_code}\n" \
     -X POST http://localhost:8041/guardar.do \
     -d "nombre=Prueba&email=prueba@test.com&telefono=600000000&id="
# Esperado: 302

# Crear sin nombre → 200 con mensaje de error
curl -s -X POST http://localhost:8041/guardar.do \
     -d "nombre=&email=x@x.com&id=" | grep -o "obligatorio"
# Esperado: obligatorio

# Editar contacto id=1
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8041/editar.do?id=1
# Esperado: 200

curl -s http://localhost:8041/editar.do?id=1 | grep -o "Ana"
# Esperado: Ana

# Eliminar contacto → 302 redirect
curl -s -o /dev/null -w "%{http_code}\n" \
     -X POST http://localhost:8041/eliminar.do -d "id=1"
# Esperado: 302
```
