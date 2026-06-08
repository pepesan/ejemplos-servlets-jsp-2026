# Docker — Entorno de bases de datos

Levanta MySQL 8 y PostgreSQL 16 con sus respectivos clientes web (phpMyAdmin y Adminer) mediante Docker Compose.

## Credenciales

| Servicio    | Host (desde host) | Puerto | Base de datos | Usuario | Contraseña |
|-------------|-------------------|--------|---------------|---------|------------|
| MySQL       | `localhost`       | 3306   | `cursosdb`    | `curso` | `curso123` |
| PostgreSQL  | `localhost`       | 5432   | `cursosdb`    | `curso` | `curso123` |
| phpMyAdmin  | http://localhost:8888 | — | —            | —       | —          |
| Adminer     | http://localhost:8889 | — | —            | —       | —          |

Los datos persisten en `docker/data/mysql/` y `docker/data/postgres/` (carpetas ignoradas por git).

---

## Scripts

### Ciclo de vida

| Script | Descripción |
|--------|-------------|
| `00_init.sh` | Crea las carpetas locales de datos (`data/mysql/`, `data/postgres/`). Se llama automáticamente desde `01_launch.sh`. |
| `01_launch.sh` | Arranca todos los servicios en segundo plano (`docker compose up -d`). |
| `02_ps.sh` | Muestra el estado de los contenedores del compose. |
| `03_logs.sh` | Sigue los logs en tiempo real. Acepta el nombre de un servicio como argumento opcional. |
| `10_destroy.sh` | Para y elimina los contenedores **y borra los datos** de `data/mysql/` y `data/postgres/`. Pide confirmación. |

```bash
./01_launch.sh          # arrancar todo
./02_ps.sh              # ver estado
./03_logs.sh            # logs de todos los servicios
./03_logs.sh mysql      # logs solo de MySQL
./10_destroy.sh         # destruir contenedores y datos (¡irreversible!)
```

### MySQL

| Script | Descripción |
|--------|-------------|
| `04_exec_mysql.sh` | Abre una shell **bash** dentro del contenedor MySQL. |
| `05_import_mysql_sql.sh <fichero.sql> [bd]` | Importa un fichero SQL en MySQL. La base de datos por defecto es `cursosdb`. |
| `06_use_mysql.sh [bd]` | Abre la consola interactiva **mysql**. La base de datos por defecto es `cursosdb`. |

```bash
./04_exec_mysql.sh                              # shell bash en el contenedor
./05_import_mysql_sql.sh sql/mysql_ejemplo.sql  # importar ejemplo
./06_use_mysql.sh                               # consola mysql (cursosdb)
./06_use_mysql.sh otra_bd                       # consola mysql (otra BD)
```

### PostgreSQL

| Script | Descripción |
|--------|-------------|
| `07_exec_postgres.sh` | Abre una shell **bash** dentro del contenedor PostgreSQL. |
| `08_import_postgres_sql.sh <fichero.sql> [bd]` | Importa un fichero SQL en PostgreSQL. La base de datos por defecto es `cursosdb`. |
| `09_use_postgres.sh [bd]` | Abre la consola interactiva **psql**. La base de datos por defecto es `cursosdb`. |

```bash
./07_exec_postgres.sh                                  # shell bash en el contenedor
./08_import_postgres_sql.sh sql/postgres_ejemplo.sql   # importar ejemplo
./09_use_postgres.sh                                   # consola psql (cursosdb)
./09_use_postgres.sh otra_bd                           # consola psql (otra BD)
```

---

## Acceso web — phpMyAdmin (MySQL)

Abre **http://localhost:8888** en el navegador.

phpMyAdmin se conecta automáticamente al contenedor MySQL con el usuario `curso`. No hace falta introducir servidor ni credenciales.

![phpMyAdmin login](https://www.phpmyadmin.net/static/images/logo-og.png)

Desde la interfaz puedes:
- Explorar tablas y datos visualmente
- Ejecutar consultas SQL en la pestaña **SQL**
- Importar ficheros `.sql` desde la pestaña **Importar**
- Exportar dumps desde la pestaña **Exportar**

---

## Acceso web — Adminer (MySQL y PostgreSQL)

Abre **http://localhost:8889** en el navegador.

Adminer es un cliente web genérico que soporta múltiples motores. Rellena el formulario de login según el servidor al que quieras conectarte:

### Conectar a MySQL

| Campo | Valor |
|-------|-------|
| Sistema | `MySQL` |
| Servidor | `mysql` |
| Usuario | `curso` |
| Contraseña | `curso123` |
| Base de datos | `cursosdb` |

### Conectar a PostgreSQL

| Campo | Valor |
|-------|-------|
| Sistema | `PostgreSQL` |
| Servidor | `postgres` |
| Usuario | `curso` |
| Contraseña | `curso123` |
| Base de datos | `cursosdb` |

> Los nombres de servidor son `mysql` y `postgres` (nombres de servicio dentro de la red Docker), no `localhost`.

Desde Adminer puedes:
- Navegar por tablas y registros
- Ejecutar SQL desde la opción **Comando SQL**
- Importar ficheros `.sql` desde **Importar**
- Exportar datos desde **Exportar**

---

## Ficheros SQL de ejemplo

En `sql/` hay dos ficheros de ejemplo con el mismo esquema (`categorias`, `articulos`, `clientes`, `pedidos`) adaptado a cada motor:

```bash
./05_import_mysql_sql.sh    sql/mysql_ejemplo.sql
./08_import_postgres_sql.sh sql/postgres_ejemplo.sql
```
