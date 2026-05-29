# 03 - Maven: Profiles

Demuestra cómo usar Maven Profiles para cambiar la configuración del proyecto según el entorno de ejecución: desarrollo local, producción e integración continua.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`source/target = 8` en el POM raíz) |

> Para configurar el JDK en IntelliJ IDEA y resolver el error "JDK isn't specified", ver la sección correspondiente en [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## ¿Qué es un Maven Profile?

Un **profile** es un bloque del `pom.xml` que sobreescribe propiedades, dependencias, plugins o recursos cuando está activo. Permite usar el **mismo código fuente** con configuraciones distintas según el contexto.

```xml
<profiles>
    <profile>
        <id>dev</id>
        <activation>
            <activeByDefault>true</activeByDefault>  <!-- activo si no se indica otro -->
        </activation>
        <properties>
            <db.url>jdbc:h2:mem:devdb</db.url>
        </properties>
    </profile>

    <profile>
        <id>prod</id>
        <properties>
            <db.url>jdbc:mysql://db.miempresa.com:3306/appdb</db.url>
        </properties>
    </profile>
</profiles>
```

---

## Profiles de este módulo

| Profile | Activación | Entorno | DB | Log |
|---------|-----------|---------|-----|-----|
| `dev` | Por defecto | Desarrollo local | H2 en memoria | DEBUG |
| `prod` | `-P prod` | Producción | MySQL remoto | WARN |
| `ci` | Variable de entorno `CI` existe | Integración continua | H2 en memoria | INFO |

---

## Activación de profiles

### 1. Por defecto (`activeByDefault`)

```xml
<activation>
    <activeByDefault>true</activeByDefault>
</activation>
```

El profile `dev` se activa si no se especifica ninguno con `-P`.

### 2. Explícita con `-P`

```bash
mvn compile exec:java -P prod      # activa prod, desactiva dev
mvn clean package -P prod          # empaqueta con configuración de producción
```

### 3. Por variable de entorno

```xml
<activation>
    <property>
        <name>env.CI</name>   <!-- se activa si la variable de entorno CI existe -->
    </property>
</activation>
```

En GitHub Actions, Jenkins o GitLab CI la variable `CI=true` se define automáticamente, activando este profile sin argumentos.

### 4. Por propiedad del sistema (`-D`)

```xml
<activation>
    <property>
        <name>entorno</name>
        <value>staging</value>
    </property>
</activation>
```

```bash
mvn package -Dentorno=staging
```

### 5. Por JDK o sistema operativo

```xml
<activation>
    <jdk>[11,17)</jdk>          <!-- activo en JDK 11, 12, 13, 14, 15, 16 -->
</activation>

<activation>
    <os>
        <family>Windows</family>
    </os>
</activation>
```

---

## Resource filtering

El **filtering** reemplaza marcadores `${propiedad}` dentro de los ficheros de recursos con los valores reales del profile activo. Permite tener un solo `application.properties` con placeholders:

```properties
# src/main/resources/application.properties
entorno=${entorno}
db.url=${db.url}
db.usuario=${db.usuario}
log.nivel=${log.nivel}
```

Maven reemplaza los `${}` en tiempo de compilación. El fichero generado en `target/classes/` ya tendrá los valores concretos.

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>   <!-- habilitar el reemplazo -->
        </resource>
    </resources>
</build>
```

---

## Clases

| Clase | Descripción |
|-------|-------------|
| `ConfiguracionApp` | Lee las 4 propiedades del sistema (`entorno`, `db.url`, `db.usuario`, `log.nivel`) con valores por defecto para `dev`. |
| `App` | `main()` que imprime la configuración activa. |

---

## Uso

```bash
# Profile dev (por defecto)
./start.sh
./start.sh dev

# Profile prod
./start.sh prod

# Profile ci (simulado con variable de entorno)
CI=true mvn compile exec:java -pl 03-maven-profiles
```

Salida esperada con `dev`:
```
──────────────────────────────────────────────────
  Demo Maven Profiles
──────────────────────────────────────────────────
  Entorno  : dev
  DB URL   : jdbc:h2:mem:devdb
  Usuario  : sa
  Log nivel: DEBUG
──────────────────────────────────────────────────
```

---

## Ver profiles disponibles

```bash
mvn help:all-profiles -pl 03-maven-profiles     # lista todos los profiles
mvn help:active-profiles -pl 03-maven-profiles  # muestra cuál está activo ahora
```

---

## Scripts

```bash
./build.sh        # mvn clean package
./start.sh [dev|prod|ci]   # ejecuta App con el profile indicado (dev por defecto)
./stop.sh         # módulo JAR, no tiene servidor
./test.sh         # mvn test
```

## Tests

| Test | Resultado esperado |
|------|--------------------|
| `ConfiguracionAppTest#valorPorDefectoEntorno` | Sin System.property → `"dev"` |
| `ConfiguracionAppTest#valorPorDefectoDbUrl` | Sin System.property → `"jdbc:h2:mem:devdb"` |
| `ConfiguracionAppTest#sobreescrituraPorSystemProperty` | System.properties prod → valores prod |
| `ConfiguracionAppTest#toStringContieneEntorno` | `toString()` incluye el entorno |
