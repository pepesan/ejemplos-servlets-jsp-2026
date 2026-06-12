# 43 — Struts 1.x: Dónde poner la validación

En Struts 1.x la validación puede vivir en cuatro sitios distintos.
El módulo 40 mostró el primero; este módulo muestra los otros tres.

> ⚠️ **Java 8 obligatorio.** Struts 1.3.x usa reflexión que Java 9+ bloquea.
> Instalar con SDKMAN: `sdk install java 8.0.412-tem && sdk use java 8.0.412-tem`

## Comparativa de los cuatro enfoques

| Módulo | ¿Dónde valida? | Mecanismo | Sin recompilar |
|--------|----------------|-----------|----------------|
| **40** | `Action.execute()` | Código Java en la acción | No |
| **43-A** | `validation.xml` | Struts Validator (declarativo) | Sí — editar el XML |
| **43-B** | `ActionForm.validate()` | Código Java en el Form | No |
| **43-C** | Anotaciones en el Form bean | Bean Validation JSR-380 (Hibernate Validator) | No |

Cada ejemplo tiene sus propias clases Java independientes; ninguna clase se comparte entre ejemplos.

---

## Cómo funciona `validate="true"` (ambos ejemplos)

```
Navegador  POST /xxx.do
    │
    ▼
ActionServlet
    ├─ 1. Crea/reutiliza el form bean y lo puebla por reflexión
    ├─ 2. validate="true"  →  llama a form.validate()
    │         ┌─ hay errores → reenvía a input="..."  (Action NO se ejecuta)
    │         └─ sin errores → continúa
    └─ 3. Llama a Action.execute()  ← solo si validate() devolvió vacío
               └─ mapping.findForward("exito") → vista de éxito
```

El atributo `input` del `<action>` es obligatorio cuando `validate="true"`:
indica a dónde reenviar cuando hay errores de validación.
El form bean (con los valores que el usuario escribió) y los errores quedan en
request scope para que la vista los muestre.

---

## Ejemplo A — Struts Validator (declarativo)

**Formulario de registro**: nombre, correo electrónico, edad (1–120).

### Cómo funciona

Las reglas se escriben en `WEB-INF/validation.xml`. En tiempo de arranque,
`ValidatorPlugIn` carga ese fichero junto con `validator-rules.xml`
(empaquetado dentro de `struts-core.jar`), que define los validadores
estándar: `required`, `minlength`, `maxlength`, `email`, `integer`, `intRange`…

Cuando Struts llama a `RegistroForm.validate()`, `ValidatorForm` lo implementa
leyendo las reglas del XML y aplicando cada validador declarado en `depends`.
No hay código de validación en ninguna clase Java del proyecto.

### `RegistroForm` — extiende `ValidatorForm`

```java
public class RegistroForm extends ValidatorForm {
    private String nombre = "";
    private String email  = "";
    private String edad   = "";
    // getters, setters, reset()
    // validate() lo hereda de ValidatorForm — delega al XML
}
```

El único cambio frente a un `ActionForm` ordinario es la herencia.

### `validation.xml` — reglas declarativas

```xml
<form name="registroForm">

    <!-- nombre: obligatorio, entre 2 y 50 caracteres -->
    <field property="nombre" depends="required,minlength,maxlength">
        <arg position="0" key="label.nombre"/>
        <arg position="1" name="minlength" key="${var:minlength}" resource="false"/>
        <arg position="1" name="maxlength" key="${var:maxlength}" resource="false"/>
        <var><var-name>minlength</var-name><var-value>2</var-value></var>
        <var><var-name>maxlength</var-name><var-value>50</var-value></var>
    </field>

    <!-- email: obligatorio, formato de correo válido -->
    <field property="email" depends="required,email">
        <arg position="0" key="label.email"/>
    </field>

    <!-- edad: obligatorio, entero, rango 1–120 -->
    <field property="edad" depends="required,integer,intRange">
        <arg position="0" key="label.edad"/>
        <arg position="1" name="intRange" key="${var:min}" resource="false"/>
        <arg position="2" name="intRange" key="${var:max}" resource="false"/>
        <var><var-name>min</var-name><var-value>1</var-value></var>
        <var><var-name>max</var-name><var-value>120</var-value></var>
    </field>

</form>
```

| Atributo / elemento | Significado |
|---------------------|-------------|
| `depends` | Validadores a aplicar en orden (deben existir en `validator-rules.xml`) |
| `<arg position="N">` | Argumento `{N}` en el mensaje de error del `.properties` |
| `resource="false"` | Usa el valor de `key` literalmente, no lo busca en el properties |
| `${var:nombre}` | Referencia al valor del `<var>` del mismo `<field>` |

### `ValidatorPlugIn` en `struts-config.xml`

```xml
<plug-in className="org.apache.struts.validator.ValidatorPlugIn">
    <set-property property="pathnames"
                  value="/org/apache/struts/validator/validator-rules.xml,
                         /WEB-INF/validation.xml"/>
</plug-in>
```

- `validator-rules.xml` — viene dentro de `struts-core.jar`; no hay que crearlo.
- `validation.xml` — el fichero del proyecto con las reglas por formulario.

### `struts-config.xml` — el action del Ejemplo A

```xml
<action path="/registro"
        type="com.cursosdedesarrollo.RegistroAction"
        name="registroForm"
        scope="request"
        validate="true"
        input="/WEB-INF/vistas/formulario.jsp">
    <forward name="exito" path="/WEB-INF/vistas/exito.jsp"/>
</action>
```

### `ApplicationResources.properties` — mensajes del Struts Validator

Las claves `errors.*` deben coincidir exactamente con las que usa `validator-rules.xml`.
Las claves `label.*` son los `arg position="0"` referenciados en `validation.xml`.

```properties
errors.required={0} es obligatorio.
errors.minlength={0} no puede tener menos de {1} caracteres.
errors.maxlength={0} no puede tener más de {1} caracteres.
errors.email={0} no es un correo electrónico válido.
errors.integer={0} debe ser un número entero.
errors.range={0} debe estar entre {1} y {2}.

label.nombre=El nombre
label.email=El correo electrónico
label.edad=La edad
```

---

## Ejemplo B — Validación programática en el Form

**Formulario de contacto**: nombre, correo electrónico, mensaje (mínimo 10 caracteres).

### Cómo funciona

`ContactoForm` extiende `ActionForm` y sobreescribe `validate()`.
El mecanismo de Struts es idéntico al Ejemplo A (`validate="true"` en struts-config),
pero la lógica de validación está en Java dentro del Form, no en XML.
La Action sigue sin tener ningún código de validación.

```java
@Override
public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();

    if (nombre == null || nombre.trim().isEmpty()) {
        errors.add("nombre", new ActionMessage("contacto.error.nombre.requerido"));
    }

    if (email == null || email.trim().isEmpty()) {
        errors.add("email", new ActionMessage("contacto.error.email.requerido"));
    } else if (!email.contains("@") || email.indexOf('@') == email.length() - 1) {
        errors.add("email", new ActionMessage("contacto.error.email.formato"));
    }

    if (mensaje == null || mensaje.trim().isEmpty()) {
        errors.add("mensaje", new ActionMessage("contacto.error.mensaje.requerido"));
    } else if (mensaje.trim().length() < 10) {
        errors.add("mensaje", new ActionMessage("contacto.error.mensaje.corto"));
    }

    return errors;   // si está vacío, Struts llama a ContactoAction.execute()
}
```

- `errors.add(property, message)` acumula errores por campo.
  `<html:errors/>` los muestra todos; `<html:errors property="nombre"/>` solo los de ese campo.
- `ActionMessage` solo almacena la clave; no resuelve el texto hasta renderizar la vista.
- El método puede recibir `null` en los dos parámetros si la validación no los necesita
  (útil para tests unitarios sin contenedor).

### `struts-config.xml` — el action del Ejemplo B

```xml
<action path="/contacto"
        type="com.cursosdedesarrollo.ContactoAction"
        name="contactoForm"
        scope="request"
        validate="true"
        input="/WEB-INF/vistas/formularioContacto.jsp">
    <forward name="exito" path="/WEB-INF/vistas/exitoContacto.jsp"/>
</action>
```

No se necesita `ValidatorPlugIn` para este ejemplo.

### `ApplicationResources.properties` — mensajes del Ejemplo B

```properties
contacto.error.nombre.requerido=El nombre es obligatorio.
contacto.error.email.requerido=El correo electrónico es obligatorio.
contacto.error.email.formato=El correo electrónico no tiene un formato válido.
contacto.error.mensaje.requerido=El mensaje es obligatorio.
contacto.error.mensaje.corto=El mensaje debe tener al menos 10 caracteres.
```

---

## UTF-8 en los mensajes de error — `Utf8MessageResourcesFactory`

`PropertyMessageResources` (la implementación por defecto de Struts) usa
`PropertyResourceBundle(InputStream)` que lee como **ISO-8859-1**.
Los acentos del `.properties` se corrompen si el fichero está en UTF-8.

Solución incluida en este módulo: subclasificar `PropertyMessageResources`
y sobreescribir `loadLocale()` para usar `InputStreamReader("UTF-8")`.

```java
// Utf8MessageResources.java — fragmento clave
PropertyResourceBundle bundle =
        new PropertyResourceBundle(new InputStreamReader(is, "UTF-8"));
```

Se registra en `struts-config.xml`:

```xml
<message-resources parameter="ApplicationResources"
                   factory="com.cursosdedesarrollo.Utf8MessageResourcesFactory"/>
```

Con esto el `.properties` se escribe en UTF-8 normal sin escapes `\uXXXX`.

---

## Ejemplo C — Bean Validation JSR-380 (Hibernate Validator)

**Formulario de alta de empleado**: nombre, correo, teléfono, NIF.

### Cómo funciona

`EmpleadoForm` extiende `BeanValidationForm` y **solo tiene anotaciones** en sus campos.
No escribe ningún método `validate()`. `BeanValidationForm` lo implementa como puente:
invoca la API estándar de `javax.validation` y convierte cada `ConstraintViolation` en un
`ActionMessage` con el texto de la anotación (`resource=false`).

```java
// BeanValidationForm — fragmento clave
private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();

@Override
public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    Set<ConstraintViolation<BeanValidationForm>> violations = FACTORY.getValidator().validate(this);
    ActionErrors errors = new ActionErrors();
    for (ConstraintViolation<?> v : violations) {
        String field = v.getPropertyPath().toString();
        errors.add(field, new ActionMessage(v.getMessage(), false));
    }
    return errors;
}
```

- `FACTORY` es estático y thread-safe: se crea una sola vez.
- `getPropertyPath().toString()` devuelve el nombre del campo (`"nombre"`, `"email"`…).
- `new ActionMessage(texto, false)` indica que el texto es literal (no clave del `.properties`).

### `EmpleadoForm` — solo anotaciones

```java
public class EmpleadoForm extends BeanValidationForm {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 80, message = "El nombre debe tener entre 2 y 80 caracteres.")
    private String nombre = "";

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    private String email = "";

    @NotBlank(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "[0-9]{9,15}", message = "El teléfono debe tener entre 9 y 15 dígitos numéricos.")
    private String telefono = "";

    @NotBlank(message = "El NIF es obligatorio.")
    @Pattern(regexp = "\\d{8}[A-Z]", message = "El NIF debe tener 8 dígitos seguidos de una letra mayúscula.")
    private String nif = "";
    // getters, setters, reset()
}
```

### Dependencias Maven (Ejemplo C)

```xml
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>2.0.1.Final</version>
</dependency>
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.2.5.Final</version>
</dependency>
<!-- EL para interpolación en tests (Tomcat ya lo provee en tiempo de ejecución) -->
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.el</artifactId>
    <version>3.0.0</version>
    <scope>test</scope>
</dependency>
```

### Notas sobre múltiples violaciones por campo

Cuando un campo vacío tiene `@NotBlank` + `@Pattern`, ambas anotaciones
disparan simultáneamente (una violación cada una). Struts acumula todos los
`ActionMessage` para ese campo y los muestra todos.
`<html:errors property="nombre"/>` en el JSP muestra solo los errores de ese campo.

---

## Estructura de clases y archivos

```
src/main/java/com/cursosdedesarrollo/
├── RegistroForm.java              extends ValidatorForm          (Ejemplo A)
├── RegistroAction.java            execute() sin validación       (Ejemplo A)
├── MostrarRegistroAction.java     muestra formulario vacío       (Ejemplo A)
├── ContactoForm.java              extends ActionForm + validate() (Ejemplo B)
├── ContactoAction.java            execute() sin validación       (Ejemplo B)
├── MostrarContactoAction.java     muestra formulario vacío       (Ejemplo B)
├── BeanValidationForm.java        puente HV → ActionErrors       (Ejemplo C)
├── EmpleadoForm.java              extends BeanValidationForm     (Ejemplo C)
├── EmpleadoAction.java            execute() sin validación       (Ejemplo C)
├── MostrarEmpleadoAction.java     muestra formulario vacío       (Ejemplo C)
├── Utf8MessageResources.java      carga .properties en UTF-8
└── Utf8MessageResourcesFactory.java

src/main/resources/
└── ApplicationResources.properties   mensajes de error (UTF-8)

src/main/webapp/WEB-INF/
├── struts-config.xml    validate="true", input=, ValidatorPlugIn, Utf8Factory
├── validation.xml       reglas declarativas (solo Ejemplo A)
├── web.xml
└── vistas/
    ├── formulario.jsp             formulario de registro (Ejemplo A)
    ├── exito.jsp                  éxito registro         (Ejemplo A)
    ├── formularioContacto.jsp     formulario de contacto (Ejemplo B)
    ├── exitoContacto.jsp          éxito contacto         (Ejemplo B)
    ├── formularioEmpleado.jsp     formulario de empleado (Ejemplo C)
    └── exitoEmpleado.jsp          éxito alta empleado    (Ejemplo C)

src/test/java/com/cursosdedesarrollo/
├── ContactoFormTest.java    9 tests de ContactoForm.validate() sin contenedor
└── EmpleadoFormTest.java   11 tests de EmpleadoForm (vía BeanValidationForm) sin contenedor
```

---

## Tests

```bash
./test.sh   # mvn test → 20 tests, 0 fallos
```

Ambas suites prueban la validación **sin contenedor** invocando `validate(null, null)`.

### `ContactoFormTest` — 9 tests (Ejemplo B)

| Test | Caso |
|------|------|
| `formularioValidoNoGeneraErrores` | todos los campos correctos → `errors.isEmpty()` |
| `nombreVacioGeneraError` | nombre `""` → error en `"nombre"` |
| `emailVacioGeneraError` | email `""` → error en `"email"` |
| `emailSinArrobaGeneraError` | `"noesuncorreo"` → error en `"email"` |
| `emailConArrobaAlFinalGeneraError` | `"nombre@"` → error en `"email"` |
| `mensajeVacioGeneraError` | mensaje `""` → error en `"mensaje"` |
| `mensajeCortoGeneraError` | mensaje de 5 chars → error en `"mensaje"` |
| `mensajeDe10CaracteresEsValido` | mensaje de 10 chars exactos → sin error |
| `varioscamposInvalidosAcumulanErrores` | tres campos vacíos → `errors.size() == 3` |

### `EmpleadoFormTest` — 11 tests (Ejemplo C)

| Test | Caso |
|------|------|
| `formularioValidoNoGeneraErrores` | todos los campos correctos → `errors.isEmpty()` |
| `nombreVacioGeneraError` | nombre `""` → error en `"nombre"` |
| `nombreDeUnCaracterGeneraError` | nombre de 1 char → error en `"nombre"` (`@Size min=2`) |
| `emailVacioGeneraError` | email `""` → error en `"email"` |
| `emailSinArrobaGeneraError` | `"noesuncorreo"` → error en `"email"` |
| `telefonoConLetrasGeneraError` | `"abc123"` → error en `"telefono"` |
| `telefonoDeMenosDe9DigitosGeneraError` | 8 dígitos → error en `"telefono"` |
| `nifSinLetraFinalGeneraError` | `"12345678"` → error en `"nif"` |
| `nifConLetraMinusculaGeneraError` | `"12345678z"` → error en `"nif"` |
| `nifCorrectoNoGeneraError` | `"12345678Z"` → sin error en `"nif"` |
| `todosCamposVaciosGeneraErrorEnCadaCampo` | 4 campos vacíos → error en cada campo |

El Ejemplo A (Struts Validator) no tiene tests unitarios: la validación XML
la ejecuta el framework y requeriría un contexto de Struts para probarse.

---

## Arrancar

```bash
./start.sh   # Tomcat embebido → http://localhost:8043
./stop.sh    # para el proceso en el puerto 8043
./build.sh   # mvn clean package
```

---

## Pruebas curl

Con el servidor arrancado (`./start.sh`):

### Ejemplo A — Struts Validator

```bash
# Portada
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8043/
# Esperado: 200

# GET formulario vacío
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8043/formulario.do
# Esperado: 200

# POST vacío → 3 mensajes "obligatorio"
curl -s -X POST http://localhost:8043/registro.do \
     -d "nombre=&email=&edad=" | grep -o "obligatorio" | wc -l
# Esperado: 3

# nombre de 1 carácter → error minlength
curl -s -X POST http://localhost:8043/registro.do \
     -d "nombre=A&email=a@b.com&edad=25" | grep -o "menos de"
# Esperado: menos de

# email sin formato válido
curl -s -X POST http://localhost:8043/registro.do \
     -d "nombre=Ana&email=noesuncorreo&edad=25" | grep -o "válido"
# Esperado: válido

# edad fuera de rango
curl -s -X POST http://localhost:8043/registro.do \
     -d "nombre=Ana&email=ana@test.com&edad=200" | grep -o "entre"
# Esperado: entre

# edad no numérica
curl -s -X POST http://localhost:8043/registro.do \
     -d "nombre=Ana&email=ana@test.com&edad=abc" | grep -o "entero"
# Esperado: entero

# POST correcto
curl -s -X POST http://localhost:8043/registro.do \
     -d "nombre=Ana&email=ana%40test.com&edad=30" | grep -o "correcto"
# Esperado: correcto
```

### Ejemplo B — `ActionForm.validate()`

```bash
# GET formulario vacío
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8043/mostrarContacto.do
# Esperado: 200

# POST vacío → 3 mensajes "obligatorio"
curl -s -X POST http://localhost:8043/contacto.do \
     -d "nombre=&email=&mensaje=" | grep -o "obligatorio" | wc -l
# Esperado: 3

# email sin @
curl -s -X POST http://localhost:8043/contacto.do \
     -d "nombre=Ana&email=noesuncorreo&mensaje=Mensaje+largo+aqui" | grep -o "válido"
# Esperado: válido

# mensaje demasiado corto
curl -s -X POST http://localhost:8043/contacto.do \
     -d "nombre=Ana&email=ana%40test.com&mensaje=corto" | grep -o "menos"
# Esperado: menos

# POST correcto
curl -s -X POST http://localhost:8043/contacto.do \
     -d "nombre=Ana&email=ana%40test.com&mensaje=Mensaje+largo+aqui" | grep -o "correctamente"
# Esperado: correctamente
```
