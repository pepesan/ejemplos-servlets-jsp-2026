# 60 - Struts 1.x + Hibernate Clásico

Módulo web (WAR) que integra Struts 1.x como capa web y Hibernate 3.x como capa de persistencia. Representa el stack completo de una aplicación Java EE legacy.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| **Java 8** | **Java 8 Temurin** | Java 8 (`release=8` en el POM raíz) |

> **Recomendado: Java 8.** Struts 1.3.x usa reflexión sobre campos privados; Hibernate 3.6.x usa Javassist para proxies de entidades. El `start.sh` incluye `--add-opens java.base/java.lang=ALL-UNNAMED` para que funcione en Java 9+ (suprime el `InaccessibleObjectException` de Javassist). Si aun así hay problemas, usar JDK 8.

### Configurar Java 8 con SDKMAN (opcional)

```bash
sdk install java 8.0.412-tem
sdk use java 8.0.412-tem
```

---

## Stack

| Capa | Tecnología |
|------|------------|
| Controlador | Struts 1.3.10 (`ActionServlet`, `Action`, `ActionForm`) |
| Vista | JSP + Struts Taglib + JSTL |
| Persistencia | Hibernate 3.6.10 (`Session`, `Transaction`, HQL) |
| Base de datos | H2 en memoria (`jdbc:h2:mem:cursodb`) |

## Arquitectura del flujo

```
Navegador → *.do → ActionServlet → Action → EmpleadoDao → Hibernate → H2
                                     ↓
                              JSP (lista / formulario)
```

## Clases principales

| Clase | Tipo | Descripción |
|-------|------|-------------|
| `Empleado` | POJO | Entidad mapeada en `Empleado.hbm.xml` |
| `EmpleadoDao` | DAO | CRUD usando `Session` de Hibernate |
| `HibernateUtil` | Utilidad | `SessionFactory` singleton |
| `HibernateListener` | `ServletContextListener` | Inicializa y destruye `SessionFactory` con el contexto web |
| `EmpleadoForm` | `ActionForm` | Campos del formulario; `validate()` devuelve `ActionErrors` |
| `ListarEmpleadosAction` | `Action` | Lee todos los empleados y hace forward a `lista.jsp` |
| `CrearEmpleadoAction` | `Action` | GET → formulario; POST → valida, guarda, redirige (PRG) |
| `EliminarEmpleadoAction` | `Action` | Borra por `id` y redirige a la lista (PRG) |

## Configuración

| Fichero | Propósito |
|---------|-----------|
| `WEB-INF/web.xml` | Registra `HibernateListener` y `ActionServlet` con patrón `*.do` |
| `WEB-INF/struts-config.xml` | `form-beans` y `action-mappings` |
| `src/main/resources/hibernate.cfg.xml` | Conexión H2, dialecto, `hbm2ddl.auto=create-drop` |
| `src/main/resources/com/cursosdedesarrollo/Empleado.hbm.xml` | Mapeo XML de la entidad |
| `src/main/resources/ApplicationResources.properties` | Mensajes de error para Struts |

## Arranque

```bash
./start.sh   # Tomcat embebido en http://localhost:8086
./stop.sh    # Para el proceso en el puerto 8086
```

## Scripts

```bash
./build.sh   # mvn clean package
./start.sh   # mvn tomcat7:run → http://localhost:8086
./stop.sh    # fuser -k 8086/tcp
./test.sh    # mvn test
```

---

## Pruebas curl

Arrancar el servidor (`./start.sh`) antes de ejecutar los curls.

```bash
# Listar empleados (redirige a la vista lista.jsp)
curl -v http://localhost:8086/listar.do

# Crear un empleado (POST con los campos del formulario)
curl -X POST http://localhost:8086/crear.do \
     -d "nombre=Ana+García&departamento=IT&salario=3500"

# Eliminar empleado con id=1
curl -v "http://localhost:8086/eliminar.do?id=1"

# Crear empleado con salario inválido → debe volver al formulario con errores
curl -X POST http://localhost:8086/crear.do \
     -d "nombre=&departamento=IT&salario=abc"
```

> Como H2 usa `create-drop`, la base de datos se reinicia cada vez que el servidor arranca.
