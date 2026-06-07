# 42 — Struts 1.x: DispatchAction CRUD

Mismo CRUD de **Contactos** que el módulo 41, pero con una sola clase
`ContactoDispatchAction` que agrupa las seis operaciones CRUD.
`DispatchAction` es el patrón de Struts que centraliza el despacho en lugar de
dispersar la lógica en clases Action separadas.

> ⚠️ **Java 8 obligatorio.** Struts 1.3.x usa reflexión que Java 9+ bloquea.
> `sdk install java 8.0.412-tem && sdk use java 8.0.412-tem`

## Diferencia clave con el módulo 41

| | Módulo 41 | Módulo 42 |
|---|---|---|
| Clases Action | 6 (`ListarAction`, `VerAction`, …) | 1 (`ContactoDispatchAction`) |
| `struts-config.xml` | 6 `<action>` | 1 `<action parameter="method">` |
| URL de listar | `/listar.do` | `/contactos.do?method=listar` |
| URL de guardar | `POST /guardar.do` | `POST /contactos.do?method=guardar` |
| Dependencia extra | ninguna | `struts-extras` (contiene `DispatchAction`) |

## Cómo funciona DispatchAction

`DispatchAction` lee el parámetro indicado por `parameter` en el mapping
(`"method"` en este caso) y llama al método correspondiente de la subclase:

```
GET /contactos.do?method=editar&id=3
         │
         ▼  ActionServlet
         │  1. busca <action path="/contactos" parameter="method">
         │  2. crea ContactoForm, llama reset(), setId("3")
         │  3. lee request.getParameter("method") → "editar"
         │  4. invoca por reflexión ContactoDispatchAction.editar(…)
         ▼
ContactoDispatchAction.editar()
         │  carga Contacto(3) del repositorio
         │  sobreescribe campos del form
         │  findForward("formulario")
         ▼
formulario.jsp
```

Si el parámetro `method` falta o no coincide con ningún método, Struts lanza
`NoSuchMethodException`. Para cambiar ese comportamiento se puede sobrescribir
`unspecified()` en la subclase.

## La clase ContactoDispatchAction

```java
public class ContactoDispatchAction extends DispatchAction {

    public ActionForward listar(…)   { /* carga lista → lista.jsp    */ }
    public ActionForward ver(…)      { /* carga contacto → detalle.jsp */ }
    public ActionForward nuevo(…)    { /* form vacío → formulario.jsp  */ }
    public ActionForward editar(…)   { /* carga y rellena form         */ }
    public ActionForward guardar(…)  { /* valida, guarda → PRG         */ }
    public ActionForward eliminar(…) { /* elimina → PRG                */ }

    ActionErrors validar(ContactoForm f) { … }   // package-visible para tests
}
```

Cada método tiene exactamente la misma firma que `Action.execute()`. Struts
los invoca via reflexión; no hay interfaz que implementar.

## struts-config.xml: un solo action

```xml
<action path="/contactos"
        type="com.cursosdedesarrollo.ContactoDispatchAction"
        name="contactoForm"
        scope="request"
        validate="false"
        parameter="method">

    <forward name="lista"      path="/WEB-INF/vistas/lista.jsp"/>
    <forward name="detalle"    path="/WEB-INF/vistas/detalle.jsp"/>
    <forward name="formulario" path="/WEB-INF/vistas/formulario.jsp"/>
    <forward name="exito"      path="/contactos.do?method=listar" redirect="true"/>
</action>
```

El forward `"exito"` con `redirect="true"` implementa el patrón PRG:
`guardar()` y `eliminar()` siempre terminan con un redirect al listar.

## El campo method en los formularios JSP

Como el formulario hace POST y el método va en la URL de acción, se usa un
campo oculto en lugar de un parámetro de query string:

```jsp
<html:form action="/contactos" method="post">
    <input type="hidden" name="method" value="guardar"/>
    <html:hidden property="id"/>
    …
</html:form>
```

Para los enlaces de editar y ver (GET), el method va en la query string:
```html
<a href="/contactos.do?method=editar&amp;id=3">Editar</a>
```

Para el formulario de eliminar (POST):
```html
<form method="post" action="/contactos.do">
    <input type="hidden" name="method" value="eliminar"/>
    <input type="hidden" name="id"     value="3"/>
    <button type="submit">Eliminar</button>
</form>
```

## Tests

```bash
./test.sh   # mvn test → 7 tests, 0 fallos
```

`ContactoDispatchActionTest` prueba sin contenedor:

| Test | Caso |
|------|------|
| `validarFormVacioDevuelveErroresDeNombreYEmail` | form vacío → 2 errores |
| `validarConSoloNombreDevuelveErrorDeEmail` | solo nombre → 1 error (email) |
| `validarConSoloEmailDevuelveErrorDeNombre` | solo email → 1 error (nombre) |
| `validarFormCompletoNoDevuelveErrores` | nombre + email → 0 errores |
| `validarNombreSoloBlancoEsError` | nombre con espacios → error |
| `repositorioGuardaYRecuperaContacto` | guardar → buscarPorId devuelve el contacto |
| `repositorioEliminaContacto` | eliminar → buscarPorId devuelve null |

## Arrancar

```bash
./start.sh   # Tomcat embebido → http://localhost:8042
./stop.sh    # mata el proceso en el puerto 8042
./build.sh   # mvn clean package
```

## Pruebas curl

Con el servidor arrancado (`./start.sh`):

```bash
# Portada
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8042/
# Esperado: 200

# Listar
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8042/contactos.do?method=listar"
# Esperado: 200

curl -s "http://localhost:8042/contactos.do?method=listar" | grep -o "Ana García"
# Esperado: Ana García

# Formulario nuevo
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8042/contactos.do?method=nuevo"
# Esperado: 200

# Crear contacto válido → 302
curl -s -o /dev/null -w "%{http_code}\n" \
     -X POST "http://localhost:8042/contactos.do?method=guardar" \
     -d "nombre=Prueba&email=prueba@test.com&telefono=600000000&id="
# Esperado: 302

# Crear sin nombre → 200 + mensaje de error
curl -s -X POST "http://localhost:8042/contactos.do?method=guardar" \
     -d "nombre=&email=x@x.com&id=" | grep -o "obligatorio"
# Esperado: obligatorio

# Ver detalle
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8042/contactos.do?method=ver&id=2"
# Esperado: 200

curl -s "http://localhost:8042/contactos.do?method=ver&id=2" | grep -o "Luis"
# Esperado: Luis

# Editar
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8042/contactos.do?method=editar&id=1"
# Esperado: 200

# Eliminar → 302
curl -s -o /dev/null -w "%{http_code}\n" \
     -X POST "http://localhost:8042/contactos.do?method=eliminar" \
     -d "id=1"
# Esperado: 302
```
