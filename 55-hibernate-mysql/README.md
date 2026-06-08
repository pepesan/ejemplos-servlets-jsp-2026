# 55 — Hibernate + MySQL

CRUD básico con Hibernate 3.6 conectado a **MySQL 8** mediante el driver oficial `com.mysql:mysql-connector-j`.

## Requisitos previos

Levantar el contenedor de MySQL del proyecto:

```bash
cd ../docker
./01_launch.sh          # arranca todos los servicios
# o solo MySQL:
docker compose up -d mysql
```

Credenciales (configuradas en `hibernate.cfg.xml`):

| Parámetro | Valor |
|-----------|-------|
| Host | `localhost:3306` |
| Base de datos | `cursosdb` |
| Usuario | `curso` |
| Contraseña | `curso123` |

Para acceder vía web: **phpMyAdmin** en http://localhost:8888 (usuario `curso`, contraseña `curso123`).

## Estructura

```
src/main/java/com/cursosdedesarrollo/
    Producto.java        # entidad JPA con anotaciones
    HibernateUtil.java   # singleton SessionFactory

src/main/resources/
    hibernate.cfg.xml    # conexión MySQL, dialect, hbm2ddl=update

src/test/java/com/cursosdedesarrollo/
    ProductoMysqlTest.java  # 4 tests: guardar, consultar HQL, actualizar, eliminar
```

## Compilar y ejecutar tests

```bash
./build.sh   # compila sin tests
./test.sh    # ejecuta los tests (requiere MySQL en marcha)
```

## Pruebas curl

Este módulo es un JAR sin servidor HTTP. Las pruebas se realizan mediante los tests JUnit.

```bash
# Ver estado de MySQL
docker exec -it docker-mysql-1 mysql -ucurso -pcurso123 cursosdb -e "SELECT * FROM productos;"
```

## Puntos clave

- `hibernate.hbm2ddl.auto=update`: crea la tabla `productos` en el primer arranque
- URL de conexión incluye `useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` para MySQL 8
- Dialecto: `MySQL5InnoDBDialect` (compatible con MySQL 8 con InnoDB)
- `GenerationType.IDENTITY` aprovecha `AUTO_INCREMENT` de MySQL
