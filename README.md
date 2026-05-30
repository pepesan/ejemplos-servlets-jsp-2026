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
| [03-maven-profiles](03-maven-profiles/README.md) | jar | Profiles dev/prod/ci, filtering de recursos, activación |

### Unidad 2 — Servlets

| Módulo | Tipo | Puerto | Contenido |
|--------|------|--------|-----------|
| [10-servlet-xml](10-servlet-xml/README.md) | war | 8010 | Servlet mapeado por `web.xml` |
| [11-servlet-anotaciones](11-servlet-anotaciones/README.md) | war | 8011 | Servlet mapeado con `@WebServlet` |
| [12-servlet-lifecycle](12-servlet-lifecycle/README.md) | war | 8012 | Ciclo de vida: `init()`, `service()`, `destroy()`, `load-on-startup`, `init-param` |
| [13-servlet-request-response](13-servlet-request-response/README.md) | war | 8013 | `HttpServletRequest` (cabeceras, params, cookies, sesión) y `HttpServletResponse` (status codes, Content-Type) |
| [14-servlet-params](14-servlet-params/README.md) | war | 8014 | Parámetros GET y POST: `getParameter`, `getParameterValues`, formularios HTML |
| [15-servlet-filtros](15-servlet-filtros/README.md) | war | 8015 | `Filter`, `FilterChain`, `FilterConfig`; filtros de log, charset y autenticación |
| [16-servlet-cookies](16-servlet-cookies/README.md) | war | 8016 | Creación, lectura y borrado de cookies; atributos `MaxAge`, `HttpOnly`, `Path` |
| [17-servlet-sesiones](17-servlet-sesiones/README.md) | war | 8017 | `HttpSession`: crear, leer, invalidar; patrón login/logout con sesión |

### Unidad 3 — JSP y JSTL

| Módulo | Tipo | Puerto | Contenido |
|--------|------|--------|-----------|
| [20-jsp-jstl](20-jsp-jstl/README.md) | war | 8083 | Directivas, scriptlets, objetos implícitos, EL y JSTL core/fmt |
| [21-jsp-datos](21-jsp-datos/README.md) | war | 8021 | Paso de datos Servlet→JSP: tipos primitivos, listas y JavaBeans con EL |
| [22-jsp-include-layout](22-jsp-include-layout/README.md) | war | 8022 | Include estático (`<%@ include %>`) vs dinámico (`<jsp:include>`), fragmentos y componentes |
| [23-jsp-formularios](23-jsp-formularios/README.md) | war | 8023 | Ciclo GET/POST, validación server-side, re-relleno de campos, patrón PRG |
| [24-jsp-jstl-funciones](24-jsp-jstl-funciones/README.md) | war | 8024 | Biblioteca `fn:`: length, substring, replace, split/join, escapeXml |
| [25-jsp-errores](25-jsp-errores/README.md) | war | 8025 | Manejo de errores: directiva `errorPage` por JSP y `<error-page>` global en `web.xml` |

### Unidad 4 — MVC

| Módulo | Tipo | Puerto | Contenido |
|--------|------|--------|-----------|
| [30-mvc](30-mvc/README.md) | war | 8084 | Front Controller con `Comando` interface: listar, ver y crear (introducción al patrón) |
| [31-mvc-crud](31-mvc-crud/README.md) | war | 8031 | CRUD completo: editar, eliminar, filtrar por categoría, formulario JSP reutilizable |

### Unidad 5 — Struts 1.x

| Módulo | Tipo | Puerto | Contenido |
|--------|------|--------|-----------|
| [40-struts1](40-struts1/README.md) | war | 8085 | `ActionForm`, `Action`, `struts-config.xml`; formulario de saludo mínimo |

### Unidad 6 — Hibernate

> ⚠️ Requiere **JDK 8**. Hibernate 3.6 usa cglib/javassist incompatible con Java 9+.

| Módulo | Tipo | Contenido |
|--------|------|-----------|
| [50-hibernate](50-hibernate/README.md) | jar | CRUD con XML puro: `hibernate.cfg.xml` + `hbm.xml`, sin ninguna anotación |
| [51-hibernate-anotaciones](51-hibernate-anotaciones/README.md) | jar | Mismo CRUD con `@Entity`, `@Id`, `@Column`, `@Transient` — `AnnotationConfiguration` |
| [52-hibernate-relaciones](52-hibernate-relaciones/README.md) | jar | `@ManyToOne`, `@OneToMany`, `@OneToOne`, `@ManyToMany` — unidireccional y bidireccional |
| [53-hibernate-hql](53-hibernate-hql/README.md) | jar | Herencia (`SINGLE_TABLE`, `JOINED`, `TABLE_PER_CLASS`) + HQL + Criteria API |

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

Cada módulo tiene `build.sh`, `start.sh`, `stop.sh` y `test.sh`. Desde la raíz hay scripts para compilar, parar y testear el conjunto, pero **no hay `start.sh` raíz**: cada módulo WAR se arranca individualmente desde su carpeta.

### Raíz

```bash
./build.sh    # mvn clean package — compila todos los módulos
./stop.sh     # para todos los módulos web (mata puertos 8010-8017, 8021-8025, 8031, 8083-8086)
./test.sh     # mvn test — lanza todos los tests
```

### Por módulo

```bash
./10-servlet-xml/build.sh
./10-servlet-xml/start.sh   # → http://localhost:8010
./10-servlet-xml/stop.sh
./10-servlet-xml/test.sh
```

## Comandos Maven

```bash
mvn clean package                            # compilar todo
mvn clean package -pl 10-servlet-xml         # compilar un módulo
mvn test                                     # tests de todo
mvn test -pl 50-hibernate                    # tests de un módulo
mvn test -pl 10-servlet-xml -Dtest=NombreDelTest
mvn tomcat7:run -pl 10-servlet-xml           # arrancar módulo web
```

### Flags del reactor multi-módulo

| Flag | Significado | Ejemplo |
|------|-------------|---------|
| `-pl <módulo>` | **Project List**: actúa solo sobre el módulo indicado | `mvn test -pl 00-maven` |
| `-pl m1,m2` | Varios módulos separados por coma | `mvn package -pl 00-maven,01-maven-dependencias` |
| `-am` | **Also Make**: compila también los módulos de los que depende `-pl` | `mvn package -pl 03-maven-profiles -am` |
| `-amd` | **Also Make Dependents**: compila también los módulos que dependen de `-pl` | `mvn package -pl 00-maven -amd` |
| `-rf <módulo>` | **Resume From**: reanuda un build fallido desde el módulo indicado | `mvn package -rf 30-mvc` |

```bash
# Compilar un módulo y todos los que necesita
mvn clean package -pl 60-struts1-hibernate -am

# Reanudar desde donde falló sin recompilar desde el inicio
mvn clean package -rf 40-struts1
```

## Estructura de carpetas (por módulo)

```
<modulo>/
├── pom.xml
├── build.sh / start.sh / stop.sh / test.sh    ← módulos; raíz no tiene start.sh
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
