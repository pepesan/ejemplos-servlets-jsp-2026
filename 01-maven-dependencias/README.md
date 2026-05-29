# 01 - Maven: Dependencias y Scopes

Demuestra los cuatro scopes de dependencias Maven con ejemplos reales en código: `compile`, `runtime`, `test` y `provided`. Incluye la gestión del árbol de dependencias transitivas, exclusiones y repositorios de artefactos.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`source/target = 8` en el POM raíz) |

`commons-lang3 3.20` y `logback-classic 1.2.x` son compatibles con Java 8, 11 y 17.

> Para configurar el JDK en IntelliJ IDEA y resolver el error "JDK isn't specified", ver la sección correspondiente en [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Clases

| Clase | Descripción |
|-------|-------------|
| `App` | `main()` que usa `commons-lang3` (compile) y `slf4j-api` (compile). La implementación de logging la aporta `logback-classic` en runtime sin ningún `import` adicional. |

## Scopes de dependencias

| Scope | Dependencia | Efecto |
|-------|-------------|--------|
| `compile` | `commons-lang3`, `slf4j-api` | Disponible en compilación, tests y en el JAR final |
| `runtime` | `logback-classic` | Solo disponible al ejecutar; **no aparece en el classpath de compilación** |
| `test` | `junit` | Solo al compilar y ejecutar tests; excluido del JAR final |
| `provided` | `javax.servlet-api` _(comentado)_ | Maven compila con ella pero no la empaqueta; la provee el contenedor |

El patrón `slf4j-api` (compile) + `logback-classic` (runtime) es la demostración más clara de `scope runtime`: el código solo importa la interfaz `Logger`; en tiempo de ejecución Maven inyecta la implementación concreta sin cambiar ni una línea de código.

## Árbol de dependencias (`mvn dependency:tree`)

```
com.cursosdedesarrollo:01-maven-dependencias:jar:1.0-SNAPSHOT
+- org.apache.commons:commons-lang3:jar:3.20.0:compile
+- org.slf4j:slf4j-api:jar:1.7.36:compile
+- ch.qos.logback:logback-classic:jar:1.2.11:runtime
|  \- ch.qos.logback:logback-core:jar:1.2.11:runtime
\- junit:junit:jar:4.13.2:test
   \- org.hamcrest:hamcrest-core:jar:1.3:test
```

- `logback-core` es **transitiva de runtime**: `logback-classic` la arrastra, y Maven la incluye automáticamente con el mismo scope.
- `hamcrest-core` es **transitiva de test**: resuelta automáticamente porque `junit` la necesita.

## Exclusión de dependencias transitivas

Cuando dos dependencias arrastran versiones distintas de una misma librería se produce un conflicto. Se resuelve excluyendo la versión no deseada:

```xml
<dependency>
    <groupId>alguna.libreria</groupId>
    <artifactId>alguna-libreria</artifactId>
    <version>1.0.0</version>
    <exclusions>
        <exclusion>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

Usa `mvn dependency:tree` para detectar qué versión está ganando y decidir qué excluir.

## Repositorios Maven

### Repositorio local

Maven descarga cada artefacto una vez y lo guarda en `~/.m2/repository`. `mvn install` deposita el artefacto del propio proyecto para que otros proyectos locales puedan usarlo.

```
~/.m2/repository/
  org/apache/commons/commons-lang3/3.20.0/
    commons-lang3-3.20.0.jar
    commons-lang3-3.20.0.pom
    commons-lang3-3.20.0-sources.jar
```

### Repositorios públicos de software libre

| Repositorio | URL | Contenido |
|-------------|-----|-----------|
| **Maven Central** | https://repo.maven.apache.org/maven2 | Repositorio oficial. +500 000 artefactos. Requiere firma GPG para publicar. |
| **Sonatype OSSRH** | https://oss.sonatype.org | Puerta de entrada para publicar en Central (proyectos OSS). |
| **JBoss / Red Hat** | https://repository.jboss.org/nexus/content/groups/public | WildFly, versiones antiguas de Hibernate, EAP. |
| **Spring Releases** | https://repo.spring.io/release | Versiones estables del ecosistema Spring. |
| **Apache Snapshots** | https://repository.apache.org/snapshots | Snapshots de Maven, Tomcat, Struts… No usar en producción. |
| **Google Android** | https://maven.google.com | AndroidX y Google Play Services. |

> **JCenter (Bintray)** cerró en mayo de 2021. Los proyectos que dependían de él deben migrar a Maven Central.

### Cómo declarar un repositorio adicional

Solo necesario cuando la dependencia no está en Maven Central:

```xml
<repositories>
    <repository>
        <id>jboss-public</id>
        <name>JBoss Public Repository</name>
        <url>https://repository.jboss.org/nexus/content/groups/public</url>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>false</enabled></snapshots>
    </repository>
</repositories>
```

### Gestores de repositorios corporativos

| Gestor | Licencia | Notas |
|--------|----------|-------|
| **Sonatype Nexus** | OSS gratis / Pro de pago | El más extendido. Soporta Maven, npm, Docker, PyPI. |
| **JFrog Artifactory** | OSS gratis / Enterprise | Buena integración con CI/CD. |
| **Apache Archiva** | Apache 2.0 (gratis) | Más ligero, ideal para equipos pequeños. |

```xml
<!-- ~/.m2/settings.xml: espejo corporativo -->
<settings>
    <mirrors>
        <mirror>
            <id>nexus-interno</id>
            <mirrorOf>*</mirrorOf>
            <url>https://nexus.miempresa.com/repository/maven-public/</url>
        </mirror>
    </mirrors>
</settings>
```

## Scripts

```bash
./build.sh   # mvn clean package
./start.sh   # mvn compile exec:java → ejecuta App.main()
./test.sh    # mvn test
./stop.sh    # módulo JAR, no tiene servidor
```

## Tests

| Test | Resultado esperado |
|------|--------------------|
| `AppTest#formatearNombreNormal` | `"  MAVEN  "` → `"Maven"` |
| `AppTest#formatearNombreNulo` | `null` → `"Desconocido"` |
| `AppTest#formatearNombreBlanco` | `"   "` → `"Desconocido"` |
| `AppTest#esNumeroTrue` | `"42"` → `true` |
| `AppTest#esNumeroFalse` | `"abc"` → `false` |
