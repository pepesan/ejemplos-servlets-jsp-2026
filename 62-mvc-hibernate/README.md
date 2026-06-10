# 62 – MVC Front Controller + Hibernate + H2

Mismo patrón arquitectónico que el módulo 32 (Front Controller → Controller → JSP/JSTL),
añadiendo Hibernate como capa de persistencia sobre una BD H2 en memoria.

## Flujo completo

```
GET /app/empleados
     │
     └─ FrontControllerServlet.service()
           │  getPathInfo() = "/empleados"
           │  getAcciones.get("/empleados")  →  controller::listar
           │
           └─ EmpleadoController.listar(req, resp)
                 │  dao.listarTodos()   ← Hibernate abre Session, lanza HQL,
                 │                        cierra Session, devuelve List<Empleado>
                 │
                 ├─ req.setAttribute("empleados", lista)   ← datos → vista
                 ├─ req.setAttribute("total",     n)
                 │
                 └─ return "lista.jsp"
           │
           └─ forward → WEB-INF/vistas/lista.jsp
                         ${empleados}  → <c:forEach items="${empleados}" var="e">
                         ${e.salario}  → <fmt:formatNumber value="${e.salario}" pattern="#,##0.00 €">
```

## Estructura

```
src/main/java/com/cursosdedesarrollo/
  Accion.java                    ← @FunctionalInterface: String ejecutar(req, resp)
  FrontControllerServlet.java    ← @WebServlet("/app/*"), tabla de rutas
  EmpleadoController.java        ← listar / ver / formularioNuevo / formularioEditar
                                    / guardar / eliminar
  HibernateUtil.java             ← singleton de SessionFactory
  HibernateListener.java         ← @WebListener: init/shutdown Hibernate, datos iniciales
  modelo/
    Empleado.java                ← entidad JPA (mapeada con hbm.xml)
    EmpleadoDao.java             ← CRUD con Hibernate Session

src/main/resources/
  hibernate.cfg.xml                         ← conexión H2, dialecto, hbm2ddl
  com/cursosdedesarrollo/modelo/
    Empleado.hbm.xml                        ← mapeo entidad ↔ tabla empleados

WEB-INF/vistas/
  lista.jsp      ← ${empleados}, ${total}, ${buscar}; c:forEach, fmt:formatNumber
  detalle.jsp    ← ${empleado.*}; fmt:formatNumber, c:if
  formulario.jsp ← ${empleado}, ${esNuevo}, ${errores}; c:if, c:forEach
```

## Rutas registradas

| Método | Ruta                      | Controller               | Vista / Acción      |
|--------|---------------------------|--------------------------|---------------------|
| GET    | `/app/empleados`          | `listar()`               | lista.jsp           |
| GET    | `/app/empleados/ver`      | `ver()`                  | detalle.jsp         |
| GET    | `/app/empleados/nuevo`    | `formularioNuevo()`      | formulario.jsp      |
| GET    | `/app/empleados/editar`   | `formularioEditar()`     | formulario.jsp      |
| POST   | `/app/empleados/guardar`  | `guardar()`              | formulario.jsp / PRG|
| POST   | `/app/empleados/eliminar` | `eliminar()`             | redirect (PRG)      |

## Diferencias respecto al módulo 32

| Aspecto          | 32 – MVC en memoria            | 62 – MVC + Hibernate           |
|------------------|--------------------------------|--------------------------------|
| Capa de datos    | `AlumnoRepositorio` (HashMap)  | `EmpleadoDao` (Hibernate Session) |
| Persistencia     | volátil (se pierde al reiniciar)| H2 in-memory (`create-drop`)  |
| Inicialización   | bloque estático en el repositorio | `HibernateListener`          |
| Búsqueda         | stream Java                    | HQL con `lower()` (sin ANTLR de Struts) |
| Clave primaria   | `int` auto-incremental manual  | `Long` gestionado por Hibernate (`native`) |

## Ciclo de vida Hibernate

```
Tomcat start
  └─ HibernateListener.contextInitialized()
       └─ HibernateUtil.getSessionFactory()   ← lee hibernate.cfg.xml
            └─ Hibernate ejecuta CREATE TABLE empleados...  (hbm2ddl=create-drop)
  └─ poblarDatosIniciales() → dao.guardar(×5)

Petición GET /app/empleados
  └─ dao.listarTodos()
       └─ openSession() → "from Empleado order by id" → close()

Tomcat stop
  └─ HibernateListener.contextDestroyed()
       └─ HibernateUtil.shutdown()   ← Hibernate ejecuta DROP TABLE
```

## Arrancar

```bash
./62-mvc-hibernate/start.sh
# o bien (necesario --add-opens para Hibernate 3.6 + JDK 9+):
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn tomcat7:run -pl 62-mvc-hibernate
```

Abre <http://localhost:8062>

## Pruebas curl

```bash
# Lista de empleados
curl -s http://localhost:8062/app/empleados | grep -o '<td>[^<]*</td>' | head -20

# Búsqueda por departamento
curl -s "http://localhost:8062/app/empleados?buscar=Ingeni" | grep "Ingeniería"

# Crear empleado
curl -s -X POST http://localhost:8062/app/empleados/guardar \
     -d "nombre=Test+Curl&departamento=IT&salario=28000&activo=on" \
     -L | grep "Test Curl"

# Validación: salario inválido
curl -s -X POST http://localhost:8062/app/empleados/guardar \
     -d "nombre=Test" | grep "obligatorio"
```

## Tests

```bash
./62-mvc-hibernate/test.sh
# Los tests usan Hibernate + H2 real (sin mocks)
```
