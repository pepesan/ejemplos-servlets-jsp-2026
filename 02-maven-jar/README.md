# 02 - Maven: Generación de JARs

Demuestra los tres tipos de JAR que Maven puede generar y cuándo usar cada uno: JAR estándar de biblioteca, fat-JAR autoejectable y sources JAR.

---

## JDK requerido

| Mínimo | Recomendado | Compilación destino |
|--------|-------------|---------------------|
| Java 8 | **Java 11 Temurin** | Java 8 (`release=8` en el POM raíz) |

`commons-lang3 3.20` es compatible con Java 8, 11 y 17. El fat-JAR generado también requiere JDK ≥ 8 para ejecutarse.

> Para configurar el JDK en IntelliJ IDEA y resolver el error "JDK isn't specified", ver la sección correspondiente en [00-maven/README.md](../00-maven/README.md#configurar-en-intellij-idea).

---

## Clases

| Clase | Tipo | Descripción |
|-------|------|-------------|
| `Calculadora` | Biblioteca | API pública sin `main()`. `sumar()`, `restar()`, `dividir()`. Usa `Validate.isTrue()` de `commons-lang3` para validar entradas. |
| `App` | Ejecutable | `main()` que instancia `Calculadora` y muestra los resultados. Usa `StringUtils.repeat()` para el separador visual. |

## Tipos de JAR generados por `mvn package`

```
target/
  02-maven-jar-1.0-SNAPSHOT.jar                        ← JAR estándar
  02-maven-jar-1.0-SNAPSHOT-jar-with-dependencies.jar  ← fat-JAR
  02-maven-jar-1.0-SNAPSHOT-sources.jar                ← sources JAR
```

| JAR | Plugin | Cómo ejecutar | Cuándo usarlo |
|-----|--------|---------------|---------------|
| Estándar | `maven-jar-plugin` | Necesita dependencias en el classpath | Bibliotecas que otros proyectos consumen |
| Fat-JAR | `maven-assembly-plugin` | `java -jar ...-jar-with-dependencies.jar` | Aplicaciones autónomas, scripts, demos |
| Sources | `maven-source-plugin` | Solo para IDEs y consumidores | Siempre en bibliotecas publicadas |

```bash
# JAR estándar — falla sin commons-lang3 en el classpath
java -jar target/02-maven-jar-1.0-SNAPSHOT.jar

# Fat-JAR — funciona de forma completamente autónoma
java -jar target/02-maven-jar-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Salida esperada:
```
────────────────────────────────────
  Demo biblioteca Calculadora
────────────────────────────────────
  2 + 3   = 5
  10 - 4  = 6
  7 / 2   = 3,50
────────────────────────────────────
```

## Plugins configurados en `pom.xml`

### `maven-jar-plugin`

Añade `Main-Class` al `MANIFEST.MF` para que el JAR estándar sepa cuál es su punto de entrada:

```xml
<archive>
    <manifest>
        <mainClass>com.cursosdedesarrollo.App</mainClass>
        <addClasspath>true</addClasspath>
        <classpathPrefix>lib/</classpathPrefix>
    </manifest>
</archive>
```

### `maven-assembly-plugin`

Empaqueta el proyecto junto con todas sus dependencias en un único JAR. Se enlaza a la fase `package` con el descriptor `jar-with-dependencies`.

### `maven-source-plugin`

Genera un JAR con el código fuente (`.java`) enlazado a la fase `package`. Estándar para cualquier biblioteca que se publique en Maven Central o en un repositorio corporativo.

## Árbol de dependencias (`mvn dependency:tree`)

```
com.cursosdedesarrollo:02-maven-jar:jar:1.0-SNAPSHOT
+- org.apache.commons:commons-lang3:jar:3.20.0:compile
\- junit:junit:jar:4.13.2:test
   \- org.hamcrest:hamcrest-core:jar:1.3:test
```

## Scripts

```bash
./build.sh   # mvn clean package → genera los tres JARs
./start.sh   # java -jar con el fat-JAR
./test.sh    # mvn test
./stop.sh    # módulo JAR, no tiene servidor
```

## Tests

| Test | Resultado esperado |
|------|--------------------|
| `CalculadoraTest#sumar` | `2 + 3 = 5` |
| `CalculadoraTest#restar` | `4 - 3 = 1` |
| `CalculadoraTest#dividir` | `5 / 2 = 2.5` |
| `CalculadoraTest#dividirPorCero` | Lanza `IllegalArgumentException` |
