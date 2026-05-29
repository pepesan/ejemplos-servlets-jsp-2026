# Servlets y JSP + MVC Clásico

Proyecto de ejemplos para el curso de tecnologías web Java clásicas. Cubre el ciclo de vida de Servlets, JSP con JSTL, el patrón MVC con Front Controller, Struts 1.x y Hibernate clásico.

**Duración:** 28 horas — ver [TEMARIO.md](TEMARIO.md) para el detalle completo.

## Requisitos

- JDK 8+ (recomendado: Java 11 Temurin vía [SDKMAN](00-maven/README.md#sdkman--gestión-de-versiones-de-jdk-y-maven))
- Maven 3.x

## Estructura del proyecto

Proyecto Maven multi-módulo (`com.cursosdedesarrollo`). El POM raíz solo define la lista de módulos y las propiedades de compilación (Java 8, UTF-8). Cada módulo declara sus propias dependencias con versiones explícitas.

### Unidad 1 — Maven

| Módulo | Tipo | Contenido |
|--------|------|-----------|
| [00-maven](00-maven/README.md) | jar | Estructura básica, ciclo de vida, fases |
| [01-maven-dependencias](01-maven-dependencias/README.md) | jar | Scopes, dependency:tree, exclusiones, repositorios |
| [02-maven-jar](02-maven-jar/README.md) | jar | JAR estándar, fat-JAR, sources JAR, MANIFEST |

### Unidad 2 — Servlets

| Módulo | Tipo | Puerto | Contenido |
|--------|------|--------|-----------|
| [10-servlet-lifecycle](10-servlet-lifecycle/README.md) | war | 8082 | `init()`, `doGet/doPost`, `destroy()`, request/response |

### Unidad 3 — JSP y JSTL

| Módulo | Tipo | Puerto | Contenido |
|--------|------|--------|-----------|
| [20-jsp-jstl](20-jsp-jstl/README.md) | war | 8083 | Directivas, scriptlets, objetos implícitos, JSTL |

### Unidad 4 — MVC

| Módulo | Tipo | Puerto | Contenido |
|--------|------|--------|-----------|
| [30-mvc](30-mvc/README.md) | war | 8084 | Patrón Front Controller con Servlet genérico |

### Unidad 5 — Struts 1.x

| Módulo | Tipo | Puerto | Contenido |
|--------|------|--------|-----------|
| [40-struts1](40-struts1/README.md) | war | 8085 | `ActionForm`, `Action`, `struts-config.xml` |

### Unidad 6 — Hibernate

| Módulo | Tipo | Contenido |
|--------|------|-----------|
| [50-hibernate](50-hibernate/README.md) | jar | Sesiones, mapeos hbm.xml, HQL |

### Unidad 7 — Integración

| Módulo | Tipo | Puerto | Contenido |
|--------|------|--------|-----------|
| [60-struts1-hibernate](60-struts1-hibernate/README.md) | war | 8086 | Struts 1.x + Hibernate juntos, stack completo |

## Versiones de dependencias

| Librería | GroupId | Versión |
|----------|---------|---------|
| Servlet API | `javax.servlet:javax.servlet-api` | 4.0.1 |
| JSP API | `javax.servlet.jsp:javax.servlet.jsp-api` | 2.3.3 |
| JSTL | `javax.servlet:jstl` | 1.2 |
| Struts Core | `org.apache.struts:struts-core` | 1.3.10 |
| Struts Taglib | `org.apache.struts:struts-taglib` | 1.3.10 |
| Hibernate | `org.hibernate:hibernate-core` | 3.6.10.Final |
| H2 (BD en memoria) | `com.h2database:h2` | 1.4.200 |
| commons-lang3 | `org.apache.commons:commons-lang3` | 3.20.0 |
| JUnit | `junit:junit` | 4.13.2 |
| Tomcat plugin | `org.apache.tomcat.maven:tomcat7-maven-plugin` | 2.2 |

> Struts 1.3.x usa `org.apache.struts` como groupId. El artifact `struts:struts` solo llega a `1.2.9`.

## Scripts

Cada módulo tiene `build.sh`, `start.sh`, `stop.sh` y `test.sh`.

### Raíz — actúa sobre todos los módulos

```bash
./build.sh    # mvn clean package
./start.sh    # arranca todos los módulos web en paralelo (puertos 8082-8086)
./stop.sh     # para todos los módulos web
./test.sh     # mvn test
```

### Por módulo

```bash
./10-servlet-lifecycle/build.sh
./10-servlet-lifecycle/start.sh   # → http://localhost:8082
./10-servlet-lifecycle/stop.sh
./10-servlet-lifecycle/test.sh
```

## Comandos Maven

```bash
mvn clean package                            # compilar todo
mvn clean package -pl 10-servlet-lifecycle   # compilar un módulo
mvn test                                     # tests de todo
mvn test -pl 50-hibernate                    # tests de un módulo
mvn test -pl 10-servlet-lifecycle -Dtest=NombreDelTest
mvn tomcat7:run -pl 10-servlet-lifecycle     # arrancar módulo web
```

## Estructura de carpetas (por módulo)

```
<modulo>/
├── pom.xml
├── build.sh / start.sh / stop.sh / test.sh
├── README.md
└── src/
    ├── main/
    │   ├── java/com/cursosdedesarrollo/
    │   ├── resources/
    │   └── webapp/WEB-INF/          ← solo módulos WAR
    │       ├── web.xml
    │       └── struts-config.xml    ← solo 40-struts1 y 60-struts1-hibernate
    └── test/
        ├── java/com/cursosdedesarrollo/
        └── resources/
```
