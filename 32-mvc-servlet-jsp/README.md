# 32 – MVC: Front Controller + Controller + JSP/JSTL

Patrón MVC con Front Controller que delega en un controlador por recurso.
La lógica de negocio reside en métodos del controller, que pasan los datos
a la vista con `request.setAttribute`. No hay un objeto Comando por acción:
todas las operaciones de un mismo recurso (Alumno) están en una sola clase.

## Flujo completo

```
GET /app/alumnos
     │
     └─ FrontControllerServlet.service()
           │  path = "/alumnos"
           │  accion = getAcciones.get("/alumnos")  →  controller::listar
           │
           └─ AlumnoController.listar(req, resp)
                 │  consulta AlumnoRepositorio
                 │
                 ├─ req.setAttribute("alumnos",   lista)   ← DATOS → VISTA
                 ├─ req.setAttribute("total",     n)
                 ├─ req.setAttribute("aprobados", k)
                 ├─ req.setAttribute("notaMedia", media)
                 │
                 └─ return "lista.jsp"
           │
           └─ forward → WEB-INF/vistas/lista.jsp
                         ${alumnos}    ← EL lee el atributo
                         <c:forEach>
                         <fmt:formatNumber value="${notaMedia}"/>
```

## Estructura de clases

```
com.cursosdedesarrollo/
  Accion.java                  ← @FunctionalInterface: String ejecutar(req, resp)
  FrontControllerServlet.java  ← @WebServlet("/app/*"), mapea ruta+método → Accion
  AlumnoController.java        ← listar / ver / formularioNuevo / formularioEditar
                                  / guardar / eliminar
  modelo/
    Alumno.java                ← POJO: id, nombre, email, nota, activo, fechaAlta
    AlumnoRepositorio.java     ← repositorio en memoria (singleton)

WEB-INF/vistas/
  lista.jsp      ← consume: alumnos, total, aprobados, suspensos, notaMedia
  detalle.jsp    ← consume: alumno
  formulario.jsp ← consume: alumno, esNuevo, errores (Map<String,String>)
```

## Rutas registradas

| Método | Ruta                    | Método del controller     | Vista            |
|--------|-------------------------|---------------------------|------------------|
| GET    | `/app/alumnos`          | `listar`                  | lista.jsp        |
| GET    | `/app/alumnos/ver`      | `ver`                     | detalle.jsp      |
| GET    | `/app/alumnos/nuevo`    | `formularioNuevo`         | formulario.jsp   |
| GET    | `/app/alumnos/editar`   | `formularioEditar`        | formulario.jsp   |
| POST   | `/app/alumnos/guardar`  | `guardar`                 | redirect o form  |
| POST   | `/app/alumnos/eliminar` | `eliminar`                | redirect         |

## Convención de retorno del controller

El método devuelve un `String` que el FrontController interpreta:

| Valor devuelto          | Lo que hace el FrontController            |
|-------------------------|-------------------------------------------|
| `"lista.jsp"`           | `forward` a `WEB-INF/vistas/lista.jsp`    |
| `"redirect:/app/alumnos"` | `sendRedirect` (patrón PRG)             |
| `null`                  | el controller ya escribió la respuesta    |

## JSTL utilizado en las vistas

| Tag | Vista | Para qué |
|-----|-------|---------|
| `c:forEach` | lista.jsp | iterar `${alumnos}` con `varStatus` |
| `c:forEach` | formulario.jsp | listar mensajes de `${errores}` |
| `c:choose / c:when / c:otherwise` | lista.jsp, detalle.jsp | calificación |
| `c:if` | lista.jsp, formulario.jsp | badge activo/inactivo, bloque de errores |
| `c:out` | todas | salida segura frente a XSS |
| `fmt:formatNumber` | lista.jsp, detalle.jsp | nota con dos decimales |
| `fmt:formatDate` | lista.jsp, detalle.jsp | fecha de alta `dd/MM/yyyy` |

## Diferencias con módulos 30/31 (Front Controller + Comando)

| | 30/31 (Comando) | 32 (Controller) |
|---|---|---|
| Abstracción | interfaz `Comando` | interfaz `Accion` (@FunctionalInterface) |
| Clases por recurso | una por acción | una por recurso (todas las acciones juntas) |
| Method references | no | sí (`controller::listar`) |
| Redirección | la clase Comando llama `resp.sendRedirect` | el controller devuelve `"redirect:..."` |

## Arrancar

```bash
./32-mvc-servlet-jsp/start.sh
# o bien:
mvn tomcat7:run -pl 32-mvc-servlet-jsp
```

Abre <http://localhost:8032>

## Pruebas curl

```bash
# Lista de alumnos
curl -s http://localhost:8032/app/alumnos | grep -o '<td>[^<]*</td>' | head -20

# Detalle del alumno 1
curl -s "http://localhost:8032/app/alumnos/ver?id=1" | grep -A1 'Nombre'

# Crear alumno nuevo (debe redirigir a /app/alumnos)
curl -s -X POST http://localhost:8032/app/alumnos/guardar \
     -d "nombre=Prueba+Curl&email=prueba@test.com&nota=7.5&activo=on" \
     -L | grep "Prueba Curl"

# Validación: nombre vacío (debe devolver formulario con error)
curl -s -X POST http://localhost:8032/app/alumnos/guardar \
     -d "nota=5" | grep "obligatorio"
```

## Tests

```bash
./32-mvc-servlet-jsp/test.sh
```
