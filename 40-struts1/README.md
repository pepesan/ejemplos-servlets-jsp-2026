# 40 — Struts 1.x: Formulario de saludo (ejemplo mínimo)

Primer ejemplo de Struts 1.x. Un formulario con un campo `nombre` que, al enviarse,
muestra un saludo personalizado. Introduce los tres pilares del framework:
**ActionForm**, **Action** y **struts-config.xml**.

> ⚠️ **Java 8 obligatorio.** Struts 1.3.x usa reflexión que Java 9+ bloquea.
> Instalar con SDKMAN: `sdk install java 8.0.412-tem && sdk use java 8.0.412-tem`

## Paralelo con el Front Controller (30-mvc)

| Struts 1.x | 30-mvc equivalente |
|------------|--------------------|
| `ActionServlet` (framework) | `FrontControllerServlet` (nuestro) |
| `struts-config.xml` | `HashMap<String, Comando>` en `init()` |
| `ActionForm` — puebla campos automáticamente | `req.getParameter()` manual |
| `Action.execute()` | `Comando.ejecutar()` |
| `ActionForward` | nombre de vista devuelto por el comando |

## Flujo de la petición

### Visión general

```
Navegador
   │  GET /saludo.do
   ▼
ActionServlet  ───────────────────────────────────────────────────────────┐
   │ 1. busca path="/saludo" en struts-config.xml                         │
   │ 2. instancia SaludoForm (o reutiliza si scope="session")             │
   │ 3. llama a SaludoForm.reset()                                        │ struts-config.xml
   │ 4. puebla SaludoForm vía reflexión:                                  │  ─ form-bean → clase Java
   │       setNombre(request.getParameter("nombre"))                      │  ─ action   → Action + Form
   │ 5. (validate="false" → omite SaludoForm.validate())                  │  ─ forward  → vistas JSP
   │ 6. llama a SaludoAction.execute(mapping, form, req, res)             │
   ▼                                                                      │
SaludoAction                                                              │
   │  casting: SaludoForm saludoForm = (SaludoForm) form                  │
   │  validarNombre(saludoForm.getNombre())                               │
   │     └─ null / vacío → forward "entrada"  ───────────────────────────►JSP entrada.jsp
   │     └─ válido       → setAttribute("saludo", "¡Hola, Ana!")          │
   │                       forward "exito"   ───────────────────────────►JSP resultado.jsp
   └──────────────────────────────────────────────────────────────────────┘
```

### Paso a paso detallado

**1. `web.xml` → toda URL `*.do` llega a `ActionServlet`**

```xml
<servlet-mapping>
    <servlet-name>action</servlet-name>
    <url-pattern>*.do</url-pattern>
</servlet-mapping>
```

`ActionServlet` es el Front Controller del framework. El desarrollador nunca lo toca.

---

**2. `ActionServlet` localiza el mapping en `struts-config.xml`**

La URL `/saludo.do` tiene path `/saludo`. Struts busca:

```xml
<action path="/saludo"
        type="com.cursosdedesarrollo.SaludoAction"   ← qué Action instanciar
        name="saludoForm"                             ← qué Form usar (enlaza con <form-bean>)
        scope="request"                               ← dónde guardar el Form
        validate="false">
```

El atributo `name="saludoForm"` resuelve al `<form-bean>` declarado más arriba:

```xml
<form-bean name="saludoForm" type="com.cursosdedesarrollo.SaludoForm"/>
```

---

**3. `ActionServlet` prepara el `ActionForm`**

Con `scope="request"`, Struts crea una instancia nueva de `SaludoForm` en cada petición.
Primero llama a `reset()` para dejarlo limpio, luego lo **puebla automáticamente por reflexión**:
por cada parámetro del request llama al setter correspondiente del Form.

```
request.getParameter("nombre") → "Ana"
         ↓  reflexión
saludoForm.setNombre("Ana")
```

El desarrollador no escribe este mapeo: basta que el nombre del campo HTML coincida
con el nombre de la propiedad del Form (`<html:text property="nombre"` → `setNombre`).

---

**4. `validate="false"`: por qué no se llama a `SaludoForm.validate()`**

Con `validate="true"`, Struts llamaría a `SaludoForm.validate()` *antes* de llegar al Action.
Si devuelve errores, Struts reenviaría directamente al forward `"input"` sin pasar por el Action.

Aquí se usa `validate="false"` para que el Action sea el único punto de validación.
Esto permite mostrar el formulario vacío en el GET inicial sin que Struts intercepte con errores.

---

**5. `SaludoAction.execute()` recibe el Form ya poblado**

```java
public ActionForward execute(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request, HttpServletResponse response) {
    SaludoForm saludoForm = (SaludoForm) form;   // casting seguro: Struts ya creó el tipo correcto
    String errorKey = validarNombre(saludoForm.getNombre());
    ...
}
```

El Action trabaja con el Form como si fuera un DTO: lee los valores ya limpios,
aplica la lógica de negocio y decide qué vista mostrar.

---

**6. `ActionForward`: el Action indica a Struts dónde continuar**

```java
return mapping.findForward("entrada");   // → /WEB-INF/vistas/entrada.jsp
return mapping.findForward("exito");     // → /WEB-INF/vistas/resultado.jsp
```

`mapping.findForward("nombre")` busca el `<forward>` declarado dentro del `<action>` en
`struts-config.xml`. Struts hace internamente un `RequestDispatcher.forward()`.
El Action nunca escribe rutas absolutas de vistas: solo nombres lógicos.

---

### Resumen de responsabilidades

| Componente | Responsabilidad | Lo escribe |
|---|---|---|
| `ActionServlet` | Front Controller, orquesta todo el ciclo | El framework (nunca el dev) |
| `struts-config.xml` | Relaciona URLs, Forms y Actions; declara los forwards | El desarrollador |
| `SaludoForm` | JavaBean que transporta los datos del formulario; poblado automáticamente por reflexión | El desarrollador |
| `SaludoAction` | Lógica de negocio; valida, construye el resultado y elige el forward | El desarrollador |
| JSP (`entrada.jsp`, `resultado.jsp`) | Vista; lee atributos del request o valores del Form | El desarrollador |

## Clases Java

| Clase | Rol |
|-------|-----|
| `SaludoForm` | Extiende `ActionForm`; campo `nombre` con getter/setter; `reset()` limpia el campo |
| `SaludoAction` | Extiende `Action`; valida con `validarNombre()` estático; deposita saludo en request |

## Configuración: struts-config.xml

```xml
<form-beans>
    <form-bean name="saludoForm" type="com.cursosdedesarrollo.SaludoForm"/>
</form-beans>

<action-mappings>
    <action path="/saludo"
            type="com.cursosdedesarrollo.SaludoAction"
            name="saludoForm"
            scope="request"
            validate="false">
        <forward name="entrada" path="/WEB-INF/vistas/entrada.jsp"/>
        <forward name="exito"   path="/WEB-INF/vistas/resultado.jsp"/>
    </action>
</action-mappings>

<message-resources parameter="ApplicationResources"/>
```

`validate="false"` → la validación la hace el propio Action, lo que permite mostrar
el formulario vacío en el primer GET sin errores prematuros.

## Taglib de Struts en entrada.jsp

```jsp
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:errors/>                          <%-- muestra los errores de ActionErrors --%>
<html:form action="/saludo.do">         <%-- genera <form action="/saludo.do" method="post"> --%>
    <html:text property="nombre"/>      <%-- genera <input name="nombre" value="..."> --%>
    <html:submit value="Saludar"/>
</html:form>
```

## Tests

```bash
./test.sh   # mvn test → 6 tests, 0 fallos
```

`SaludoActionTest` prueba el método estático `validarNombre()` directamente, sin contenedor:

| Test | Caso |
|------|------|
| `nombreNullEsInvalido` | `null` → devuelve clave de error |
| `nombreVacioEsInvalido` | `""` → devuelve clave de error |
| `nombreSoloBlancoEsInvalido` | `"   "` → devuelve clave de error |
| `nombreValidoDevuelveNull` | `"Ana"` → devuelve `null` |
| `nombreConEspaciosValidoDevuelveNull` | `"  Ana García  "` → devuelve `null` |
| `errorDevuelveClaveDeRecurso` | la clave es `"error.nombre.requerido"` |

## Arrancar

```bash
./start.sh   # Tomcat embebido → http://localhost:8085
./stop.sh    # para el proceso en el puerto 8085
./build.sh   # mvn clean package
```

## Pruebas curl

Con el servidor arrancado (`./start.sh`):

```bash
# Portada
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8085/
# Esperado: 200

# GET /saludo.do → muestra el formulario vacío
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8085/saludo.do
# Esperado: 200

# POST con nombre vacío → vuelve al formulario con error
curl -s -X POST http://localhost:8085/saludo.do -d "nombre=" | grep -o "obligatorio"
# Esperado: obligatorio

# POST con nombre válido → muestra el saludo
curl -s -X POST http://localhost:8085/saludo.do -d "nombre=Ana" | grep -o "Hola"
# Esperado: Hola

curl -s -X POST http://localhost:8085/saludo.do -d "nombre=Ana" | grep -o "Ana"
# Esperado: Ana
```
