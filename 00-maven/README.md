# 00 - Maven Básico

Punto de partida. Muestra la estructura mínima de un proyecto Maven, el ciclo de vida completo y cómo compilar varias clases relacionadas sin dependencias externas.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`source/target = 8` en el POM raíz) |

Este módulo no usa librerías de terceros, por lo que cualquier JDK ≥ 8 funciona sin problema.

### Configurar en IntelliJ IDEA

> **Error "JDK isn't specified for module"**: ocurre cuando IntelliJ no ha importado el proyecto como Maven. Sigue los pasos siguientes para resolverlo.

**1. Abrir el proyecto como Maven** (forma correcta, solo una vez):

- `File → Open` → seleccionar el `pom.xml` **raíz** del proyecto
- En el diálogo elegir **"Open as Project"**
- IntelliJ importa todos los módulos y detecta el JDK del `pom.xml` automáticamente

**2. Asignar el SDK al proyecto:**

- `File → Project Structure` (Ctrl+Alt+Shift+S / ⌘+;)
- `Project Settings → Project`
  - `SDK` → seleccionar `java-11-temurin` (o el JDK instalado ≥ 8)
  - `Language level` → `8 - Lambdas, type annotations etc.`
- `Apply → OK`

**3. Si persiste el error en un módulo concreto:**

- `File → Project Structure → Project Settings → Modules`
- Seleccionar el módulo → pestaña `Dependencies`
- `Module SDK` → `Project SDK` (para heredar) o seleccionar directamente
- `Apply → OK`

**4. Sincronizar Maven:**

- Ventana `Maven` (lateral derecho) → botón `Reload All Maven Projects`
- O clic derecho sobre `pom.xml` → `Maven → Reload project`

**5. Verificar desde terminal:**

```bash
java -version   # debe mostrar ≥ 1.8
mvn -version    # debe mostrar el Java home correcto
```

---

## Entorno de desarrollo

### SDKMAN — gestión de versiones de JDK y Maven

SDKMAN permite instalar y cambiar entre múltiples versiones de JDK, Maven y Gradle sin tocar la configuración del sistema.

```bash
# Instalar SDKMAN (Linux / macOS / WSL)
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

sdk list java                      # ver distribuciones disponibles
sdk install java 11.0.23-tem       # instalar Temurin 11 (recomendado para este curso)
sdk default java 11.0.23-tem       # establecer como versión por defecto
sdk current java                   # ver versión activa

sdk install maven 3.9.6            # instalar Maven
sdk current maven
```

| Distribución | ID SDKMAN | Notas |
|---|---|---|
| Eclipse Temurin | `tem` | La más usada. Mantenida por Adoptium |
| Amazon Corretto | `amzn` | Soporte a largo plazo, optimizada para AWS |
| GraalVM CE | `graalce` | Permite compilar a binario nativo |
| Zulu (Azul) | `zulu` | Amplio soporte de versiones LTS |

> Para este curso se recomienda **Java 8 o Java 11 Temurin**: compatibles con Tomcat 9, Struts 1.x e Hibernate 3.x.

### IDEs recomendados

| IDE | Licencia | Maven | Jakarta EE | Descarga |
|-----|----------|-------|------------|---------|
| IntelliJ IDEA Community | Gratis | Nativo | Básico | jetbrains.com/idea |
| IntelliJ IDEA Ultimate | Pago / licencia estudiante | Nativo | Completo | jetbrains.com/idea |
| Eclipse Enterprise | Gratis | Plugin m2e | Completo | eclipse.org/downloads |
| VS Code + Extension Pack | Gratis | Extensión | Parcial | code.visualstudio.com |
| NetBeans | Gratis | Nativo | Completo | netbeans.apache.org |

---

## Clases

| Clase | Descripción |
|-------|-------------|
| `Persona` | POJO con `nombre`, `edad` y `esMayorDeEdad()`. Sin dependencias externas. |
| `App` | `main()` que crea instancias de `Persona` y llama a `saludar()`. |

## Estructura del proyecto

```
00-maven/
├── pom.xml
├── build.sh / start.sh / stop.sh / test.sh
└── src/
    ├── main/java/com/cursosdedesarrollo/
    │   ├── App.java
    │   └── Persona.java
    └── test/java/com/cursosdedesarrollo/
        ├── AppTest.java
        └── PersonaTest.java
```

## Árbol de dependencias (`mvn dependency:tree`)

```
com.cursosdedesarrollo:00-maven:jar:1.0-SNAPSHOT
\- junit:junit:jar:4.13.2:test
   \- org.hamcrest:hamcrest-core:jar:1.3:test
```

`hamcrest-core` es una **dependencia transitiva**: Maven la resuelve automáticamente porque `junit` la necesita, sin que esté declarada en el `pom.xml`.

## Tipos de empaquetado en Maven

El elemento `<packaging>` del `pom.xml` determina qué artefacto genera la fase `package` y qué plugin se encarga de construirlo.

```xml
<project>
    ...
    <packaging>jar</packaging>   <!-- valor por defecto si se omite -->
    ...
</project>
```

### `jar` — Java Archive

El tipo más común. Empaqueta las clases compiladas en un archivo `.jar`.

```xml
<packaging>jar</packaging>
```

```
target/mi-proyecto-1.0-SNAPSHOT.jar
```

- Usado en: librerías, aplicaciones de escritorio, utilidades.
- Plugin responsable: `maven-jar-plugin` (incluido por defecto).
- Se puede hacer ejecutable añadiendo `Main-Class` al `MANIFEST.MF` (ver módulo `02-maven-jar`).
- Ejemplos en este proyecto: `00-maven`, `01-maven-dependencias`, `02-maven-jar`, `50-hibernate`.

---

### `war` — Web Application Archive

Empaqueta una aplicación web Java EE lista para desplegar en un contenedor de servlets (Tomcat, JBoss, WebLogic).

```xml
<packaging>war</packaging>
```

Estructura interna del WAR:

```
mi-app.war
├── index.jsp
├── WEB-INF/
│   ├── web.xml          ← descriptor de despliegue
│   ├── classes/         ← clases compiladas (Servlets, Actions…)
│   └── lib/             ← dependencias con scope compile/runtime
└── META-INF/
```

- Las dependencias con scope `provided` (ej: `javax.servlet-api`) **no se incluyen** en `WEB-INF/lib` porque el contenedor ya las aporta.
- Plugin responsable: `maven-war-plugin` (incluido por defecto cuando el packaging es `war`).
- Ejemplos en este proyecto: `10-servlet-lifecycle`, `20-jsp-jstl`, `30-mvc`, `40-struts1`, `60-struts1-hibernate`.

```xml
<!-- Configuración habitual del maven-war-plugin -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.4.0</version>
    <configuration>
        <failOnMissingWebXml>false</failOnMissingWebXml>  <!-- Servlet 3.0+ -->
    </configuration>
</plugin>
```

---

### `ear` — Enterprise Application Archive

Agrupa varios módulos (WARs, JARs, EJBs) en un único artefacto desplegable en un servidor de aplicaciones Java EE completo (JBoss EAP, WebLogic, WebSphere).

```xml
<packaging>ear</packaging>
```

Estructura interna del EAR:

```
mi-empresa.ear
├── mi-web.war
├── mi-ejb.jar
└── META-INF/
    └── application.xml   ← descriptor con la lista de módulos
```

- Requiere el `maven-ear-plugin`.
- Menos habitual hoy en día; los proyectos modernos prefieren despliegues individuales.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-ear-plugin</artifactId>
    <version>3.3.0</version>
    <configuration>
        <modules>
            <webModule>
                <groupId>com.cursosdedesarrollo</groupId>
                <artifactId>mi-web</artifactId>
                <contextRoot>/app</contextRoot>
            </webModule>
        </modules>
    </configuration>
</plugin>
```

---

### `pom` — Project Object Model

No genera bytecode ni artefacto binario. Se usa en dos casos:

**1. Proyecto padre (agregador):** contiene la lista de módulos hijos y configuración común.

```xml
<packaging>pom</packaging>

<modules>
    <module>00-maven</module>
    <module>10-servlet-lifecycle</module>
</modules>
```

**2. BOM (Bill of Materials):** centraliza versiones de dependencias para que los proyectos hijos las hereden sin repetirlas.

```xml
<packaging>pom</packaging>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.2.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

- Ejemplo en este proyecto: el `pom.xml` raíz de `ejemplo-servlets-jsp-2026`.

---

### Resumen comparativo

| Packaging | Artefacto | Desplegado en | Uso típico |
|-----------|-----------|---------------|------------|
| `jar` | `.jar` | JVM directamente | Librerías, CLIs, microservicios |
| `war` | `.war` | Tomcat, JBoss, WebLogic | Aplicaciones web Java EE |
| `ear` | `.ear` | Servidor Java EE completo | Aplicaciones empresariales multi-módulo |
| `pom` | _(ninguno)_ | — | Proyectos padre, BOMs |

---

## Ciclo de vida Maven

```
validate → compile → test → package → install
```

```bash
mvn validate        # valida que pom.xml es correcto
mvn compile         # compila src/main/java → target/classes/
mvn test            # compila src/test/java y ejecuta los tests
mvn package         # empaqueta → target/00-maven-1.0-SNAPSHOT.jar
mvn install         # instala el JAR en ~/.m2/repository/
mvn clean           # elimina target/
mvn dependency:tree # muestra el árbol de dependencias
```

## Scripts

```bash
./build.sh   # mvn clean package
./start.sh   # ejecuta App.main()
./test.sh    # mvn test
./stop.sh    # módulo JAR, no tiene servidor
```

## Tests

| Test | Resultado esperado |
|------|--------------------|
| `AppTest#saludarAdulto` | `"Hola, Ana"` |
| `AppTest#saludarMenor` | `"Hola, Luis (menor de edad)"` |
| `PersonaTest#mayorDeEdad` | `true` para edad ≥ 18 |
| `PersonaTest#menorDeEdad` | `false` para edad < 18 |
| `PersonaTest#toStringFormato` | `"Ana (30 años)"` |
