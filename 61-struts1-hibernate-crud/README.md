# 61 — Struts 1.x + Hibernate: CRUD completo

Resumen del curso. CRUD completo de **Empleado** (listar, buscar, mostrar, añadir, editar, borrar)
con el stack completo: **Struts 1.x DispatchAction** + **Hibernate 3.6** (mapeo `hbm.xml`) + **H2** en memoria.

> ⚠️ **Recomendado: Java 8.** El `start.sh` incluye `--add-opens java.base/java.lang=ALL-UNNAMED`
> para que Javassist funcione en Java 9+. Si aun así hay problemas usar JDK 8:
> `sdk install java 8.0.412-tem && sdk use java 8.0.412-tem`

## Capas de la aplicación

```
Navegador
   │
   ▼ web.xml
   │  SetCharacterEncodingFilter  → fuerza UTF-8 en POST
   │  HibernateListener           → abre/cierra SessionFactory
   │  ActionServlet               → recibe *.do
   │
   ▼ struts-config.xml
   │  <action path="/empleados" parameter="method">
   │
   ▼ EmpleadoCrudDispatchAction   ← Controlador (DispatchAction)
   │  lee method=listar/mostrar/nuevo/editar/guardar/eliminar
   │  usa EmpleadoForm            ← Modelo/Form (ActionForm)
   │  usa EmpleadoDao             ← Modelo/DAO (Hibernate Sessions)
   │
   ▼ HibernateUtil                ← SessionFactory singleton
   │  hibernate.cfg.xml + Empleado.hbm.xml
   │
   ▼ H2 (mem:cursodb61)           ← BD en memoria
   │
   ▼ JSP (lista / detalle / formulario)  ← Vista (JSTL + html taglib)
```

## Estructura de clases

| Clase | Capa | Responsabilidad |
|---|---|---|
| `Empleado` | Modelo — Entidad | POJO mapeado a tabla `empleados` via `hbm.xml` |
| `EmpleadoDao` | Modelo — DAO | Hibernate Sessions: guardar, buscarPorId, listarTodos, buscar, actualizar, eliminar |
| `EmpleadoForm` | Modelo — Form | Transporta datos del formulario; valida nombre, departamento y salario |
| `EmpleadoCrudDispatchAction` | Controlador | Coordina Form, DAO y vistas; despacha por `method=` |
| `HibernateUtil` | Infraestructura | `SessionFactory` singleton (se inicializa una vez) |
| `HibernateListener` | Infraestructura | Arranca Hibernate al iniciar la webapp; puebla datos iniciales |

## Flujo de cada operación

### listar (con búsqueda opcional)

```
GET /empleados.do?method=listar[&busqueda=ana]
   ▼ EmpleadoCrudDispatchAction.listar()
   │  ef.getBusqueda() → "ana" (o vacío)
   │  ┌─ vacío  → dao.listarTodos()
   │  └─ texto  → dao.buscar("ana")  ← Criteria API: ilike nombre OR departamento
   │  request["empleados"] = lista
   ▼ lista.jsp  — tabla + barra de búsqueda
```

### mostrar

```
GET /empleados.do?method=mostrar&id=3
   ▼ EmpleadoCrudDispatchAction.mostrar()
   │  dao.buscarPorId(3) → Empleado
   │  request["empleado"] = empleado
   ▼ detalle.jsp
```

### editar → guardar (modificación)

```
GET /empleados.do?method=editar&id=3
   ▼ editar()
   │  carga Empleado(3) del repositorio
   │  rellena EmpleadoForm con sus datos
   ▼ formulario.jsp  — título "Editar empleado #3", id oculto

POST /empleados.do?method=guardar  id=3&nombre=Ana2&...
   ▼ guardar()
   │  ef.validate() → OK
   │  id > 0 → dao.buscarPorId(3) → dao.actualizar(e)
   │  findForward("exito")  →  redirect /empleados.do?method=listar  (PRG)
```

### nuevo → guardar (alta)

```
GET /empleados.do?method=nuevo
   ▼ nuevo()  →  EmpleadoForm.reset()  →  formulario.jsp vacío

POST /empleados.do?method=guardar  id=&nombre=Carlos&...
   ▼ guardar()
   │  id vacío → new Empleado() → dao.guardar(e)  (id asignado por Hibernate)
   ▼  redirect /empleados.do?method=listar  (PRG)
```

### eliminar

```
POST /empleados.do?method=eliminar  id=3
   ▼ eliminar()
   │  dao.eliminar(3)
   ▼  redirect /empleados.do?method=listar  (PRG)
```

## Búsqueda con Criteria API

El método `EmpleadoDao.buscar()` usa Criteria API en lugar de HQL con `lower()`:

```java
s.createCriteria(Empleado.class)
    .add(Restrictions.or(
        Restrictions.ilike("nombre",       "%" + texto + "%"),
        Restrictions.ilike("departamento", "%" + texto + "%")
    ))
    .addOrder(Order.asc("id"))
    .list();
```

`ilike` es insensible a mayúsculas. Se usa Criteria en vez de HQL con `lower()` para
evitar el conflicto de versiones de ANTLR entre Struts (`antlr:2.7.2`) e Hibernate (`antlr:2.7.6`).

## Datos iniciales

`HibernateListener.poblarDatosIniciales()` inserta 5 empleados al arrancar si la tabla está vacía:

| Nombre | Departamento | Salario |
|---|---|---|
| Ana García | Ingeniería | 42 000 € |
| Luis Pérez | Marketing | 35 000 € |
| María López | RRHH | 38 000 € |
| Carlos Sánchez | Ingeniería | 45 000 € |
| Elena Martín | Finanzas | 40 000 € |

## Tests

```bash
./test.sh   # mvn test → 16 tests, 0 fallos
```

| Clase de test | Tests | Qué prueba |
|---|---|---|
| `EmpleadoDaoTest` | 9 | guardar, buscarPorId, listarTodos, actualizar, eliminar, buscar (nombre, dept, sin coincidencias) |
| `EmpleadoFormTest` | 7 | validación de nombre/departamento/salario; salario numérico, negativo, cero; busqueda no afecta validación |

## Arrancar

```bash
./start.sh   # Tomcat embebido → http://localhost:8061
./stop.sh    # mata el proceso en el puerto 8061
./build.sh   # mvn clean package
```

## Pruebas curl

Con el servidor arrancado (`./start.sh`):

```bash
# Portada
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8061/
# Esperado: 200

# Listar
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8061/empleados.do?method=listar"
# Esperado: 200

curl -s "http://localhost:8061/empleados.do?method=listar" | grep -o "Ana García"
# Esperado: Ana García

# Buscar
curl -s "http://localhost:8061/empleados.do?method=listar&busqueda=ingenier" | grep -o "Carlos"
# Esperado: Carlos

# Mostrar detalle
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8061/empleados.do?method=mostrar&id=1"
# Esperado: 200

# Formulario nuevo
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8061/empleados.do?method=nuevo"
# Esperado: 200

# Crear empleado → 302
curl -s -o /dev/null -w "%{http_code}\n" \
     -X POST "http://localhost:8061/empleados.do?method=guardar" \
     -d "nombre=Prueba&departamento=QA&salario=30000&id="
# Esperado: 302

# Crear sin nombre → 200 con errores
curl -s -X POST "http://localhost:8061/empleados.do?method=guardar" \
     -d "nombre=&departamento=QA&salario=30000&id=" | grep -o "obligatorio"
# Esperado: obligatorio

# Editar
curl -s -o /dev/null -w "%{http_code}\n" \
     "http://localhost:8061/empleados.do?method=editar&id=1"
# Esperado: 200

# Eliminar → 302
curl -s -o /dev/null -w "%{http_code}\n" \
     -X POST "http://localhost:8061/empleados.do?method=eliminar" \
     -d "id=1"
# Esperado: 302
```
