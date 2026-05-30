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

```
GET /saludo.do
   │
   ▼ ActionServlet (web.xml: *.do → ActionServlet)
   │  Lee struts-config.xml → path="/saludo" → SaludoAction
   │  Crea SaludoForm, puebla campos desde parámetros del request
   ▼
SaludoAction.execute()
   │  nombre vacío (primer acceso) → forward "entrada"
   ▼
/WEB-INF/vistas/entrada.jsp   (muestra el formulario)

─────────────────────────────────────────
POST /saludo.do  nombre=Ana
   ▼ ActionServlet → SaludoForm poblado → SaludoAction.execute()
   │  nombre="Ana" → OK
   │  request.setAttribute("saludo", "¡Hola, Ana!")
   │  forward "exito"
   ▼
/WEB-INF/vistas/resultado.jsp
```

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
