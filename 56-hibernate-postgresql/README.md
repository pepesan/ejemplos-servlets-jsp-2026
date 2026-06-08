# 56 — Hibernate + PostgreSQL

CRUD básico con Hibernate 3.6 conectado a **PostgreSQL 16** mediante el driver oficial `org.postgresql:postgresql`.

## Requisitos previos

Levantar el contenedor de PostgreSQL del proyecto:

```bash
cd ../docker
./01_launch.sh          # arranca todos los servicios
# o solo PostgreSQL:
docker compose up -d postgres
```

Credenciales (configuradas en `hibernate.cfg.xml`):

| Parámetro | Valor |
|-----------|-------|
| Host | `localhost:5432` |
| Base de datos | `cursosdb` |
| Usuario | `curso` |
| Contraseña | `curso123` |

Para acceder vía web: **Adminer** en http://localhost:8889 (sistema `PostgreSQL`, servidor `postgres`, usuario `curso`, contraseña `curso123`, BD `cursosdb`).

## Estructura

```
src/main/java/com/cursosdedesarrollo/
    Producto.java        # entidad JPA con anotaciones
    HibernateUtil.java   # singleton SessionFactory

src/main/resources/
    hibernate.cfg.xml    # conexión PostgreSQL, dialect, hbm2ddl=update

src/test/java/com/cursosdedesarrollo/
    ProductoPostgresTest.java  # 4 tests: guardar, consultar HQL, actualizar, eliminar
```

## Compilar y ejecutar tests

```bash
./build.sh   # compila sin tests
./test.sh    # ejecuta los tests (requiere PostgreSQL en marcha)
```

## Pruebas curl

Este módulo es un JAR sin servidor HTTP. Las pruebas se realizan mediante los tests JUnit.

```bash
# Ver estado de PostgreSQL
docker exec -it docker-postgres-1 psql -U curso -d cursosdb -c "SELECT * FROM productos;"
```

## Puntos clave

- `hibernate.hbm2ddl.auto=update`: crea la tabla `productos` en el primer arranque
- Dialecto: `PostgreSQLDialect` (Hibernate 3.6)
- `GenerationType.IDENTITY` aprovecha las secuencias implícitas de PostgreSQL (`SERIAL`)
- A diferencia de MySQL, PostgreSQL distingue mayúsculas/minúsculas en nombres de tabla si se usan comillas; Hibernate los genera en minúsculas por defecto
