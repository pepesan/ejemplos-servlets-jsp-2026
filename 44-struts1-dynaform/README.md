# 44 — Struts 1.x: DynaValidatorForm (formulario sin clase Java)

Muestra cómo definir un formulario Struts **sin escribir ninguna clase Java** para él.
Los campos se declaran en `struts-config.xml` mediante `<form-property>` y Struts
instancia `DynaValidatorForm` en tiempo de ejecución. La validación declarativa
(`validation.xml`) funciona exactamente igual que en el módulo 43-A.

> ⚠️ **Java 8 obligatorio.** Struts 1.3.x usa reflexión que Java 9+ bloquea.
> `sdk install java 8.0.412-tem && sdk use java 8.0.412-tem`

---

## Diferencia con el módulo 43-A

| | Módulo 43-A | Módulo 44 |
|---|---|---|
| Clase Java del form | `RegistroForm extends ValidatorForm` | No existe |
| Declaración de campos | Propiedades Java (`private String nombre`) | `<form-property>` en struts-config.xml |
| Acceso en la Action | `form.getNombre()` | `(String) dynaForm.get("nombre")` |
| Acceso en el JSP | `${registroForm.nombre}` | igual — DynaBean es transparente para EL |
| `validation.xml` | idéntico | idéntico |
| Tests unitarios del form | Sí (getter/setter) | No aplica (sin clase Java) |

---

## Cómo funciona `DynaValidatorForm`

Struts lee los `<form-property>` de `struts-config.xml` al arrancar y construye
internamente un `DynaClass` con esas propiedades. Cada petición instancia un
`DynaValidatorForm` que actúa como un `Map` tipado.

El framework puebla los campos igual que con un `ActionForm` ordinario
(por reflexión sobre los nombres de los parámetros del request), sin que
el desarrollador escriba setters.

```
request.getParameter("nombre") → "Ana"
              ↓  reflexión sobre DynaClass
dynaForm.set("nombre", "Ana")   ← equivalente a form.setNombre("Ana")
```

### Declaración en `struts-config.xml`

```xml
<form-bean name="registroForm"
           type="org.apache.struts.validator.DynaValidatorForm">
    <form-property name="nombre" type="java.lang.String" initial=""/>
    <form-property name="email"  type="java.lang.String" initial=""/>
    <form-property name="edad"   type="java.lang.String" initial=""/>
</form-bean>
```

- `type` — clase Java del tipo del campo; debe ser completo (`java.lang.String`, no `String`)
- `initial` — valor cuando el form se resetea; evita `null` en los campos

### Acceso en la Action

```java
DynaValidatorForm dynaForm = (DynaValidatorForm) form;

String nombre = (String) dynaForm.get("nombre");   // cast al tipo declarado
String email  = (String) dynaForm.get("email");
String edad   = (String) dynaForm.get("edad");
```

El método `get(String name)` devuelve `Object`; el cast es necesario porque
el compilador no conoce el tipo en tiempo de compilación.
Si el nombre del campo no existe, lanza `IllegalArgumentException` en runtime.

### Acceso en las JSPs

```jsp
<html:text property="nombre"/>     <%-- escribe el valor actual al mostrar el form --%>
${registroForm.nombre}             <%-- EL accede vía DynaBean → .get("nombre") --%>
```

La taglib de Struts y el EL de JSTL usan `commons-beanutils` internamente,
que reconoce `DynaBean` y llama a `get("propiedad")` en lugar de buscar un getter.
El JSP no necesita saber si el form es dinámico o tiene clase Java.

---

## `validation.xml` — sin cambios respecto al módulo 43-A

```xml
<form name="registroForm">

    <field property="nombre" depends="required,minlength,maxlength">
        <arg position="0" key="label.nombre"/>
        <arg position="1" name="minlength" key="${var:minlength}" resource="false"/>
        <arg position="1" name="maxlength" key="${var:maxlength}" resource="false"/>
        <var><var-name>minlength</var-name><var-value>2</var-value></var>
        <var><var-name>maxlength</var-name><var-value>50</var-value></var>
    </field>

    <field property="email" depends="required,email">
        <arg position="0" key="label.email"/>
    </field>

    <field property="edad" depends="required,integer,intRange">
        <arg position="0" key="label.edad"/>
        <arg position="1" name="intRange" key="${var:min}" resource="false"/>
        <arg position="2" name="intRange" key="${var:max}" resource="false"/>
        <var><var-name>min</var-name><var-value>1</var-value></var>
        <var><var-name>max</var-name><var-value>120</var-value></var>
    </field>

</form>
```

El nombre `registroForm` debe coincidir con el `name` del `<form-bean>` en struts-config.xml.

---

## `struts-config.xml` — el action

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

El action de mostrar el formulario vacío usa `forward` directo sin clase Action:

```xml
<action path="/formulario"
        name="registroForm"
        scope="request"
        validate="false"
        forward="/WEB-INF/vistas/formulario.jsp"/>
```

---

## ¿Cuándo usar `DynaValidatorForm`?

**Usar cuando:**
- El formulario es simple: solo transporte de datos hacia la Action.
- No se necesita lógica personalizada en el form (ni conversiones, ni métodos de negocio).
- Se quiere reducir el número de clases Java del proyecto.

**Preferir una clase Java cuando:**
- Se necesita `validate()` programático (como en el módulo 43-B).
- Los campos requieren tipos no-String (`Integer`, `Date`, listas) que necesitan conversión.
- Se quieren tests unitarios directos del form.
- El formulario tiene lógica de negocio (valores derivados, validaciones cruzadas).

---

## Estructura de archivos

```
44-struts1-dynaform/
├── src/main/java/com/cursosdedesarrollo/
│   ├── RegistroAction.java              ← usa DynaValidatorForm.get()
│   ├── Utf8MessageResources.java        ← carga .properties en UTF-8
│   └── Utf8MessageResourcesFactory.java
├── src/main/resources/
│   └── ApplicationResources.properties
└── src/main/webapp/WEB-INF/
    ├── struts-config.xml   ← <form-property> en lugar de clase Java
    ├── validation.xml      ← idéntico al módulo 43-A
    ├── web.xml
    └── vistas/
        ├── formulario.jsp
        └── exito.jsp
```

No hay fichero de test: sin clase Java de form no hay lógica que probar
sin contenedor. La validación la ejecuta el framework y se verifica en el servidor.

---

## Arrancar

```bash
./start.sh   # Tomcat embebido → http://localhost:8044
./stop.sh    # para el proceso en el puerto 8044
./build.sh   # mvn clean package
```

---

## Pruebas curl

```bash
# Portada
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8044/
# Esperado: 200

# GET formulario vacío
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8044/formulario.do
# Esperado: 200

# POST vacío → 3 "obligatorio"
curl -s -X POST http://localhost:8044/registro.do \
     -d "nombre=&email=&edad=" | grep -o "obligatorio" | wc -l
# Esperado: 3

# nombre de 1 carácter → error minlength
curl -s -X POST http://localhost:8044/registro.do \
     -d "nombre=A&email=a@b.com&edad=25" | grep -o "menos de"
# Esperado: menos de

# email inválido
curl -s -X POST http://localhost:8044/registro.do \
     -d "nombre=Ana&email=noesuncorreo&edad=25" | grep -o "válido"
# Esperado: válido

# edad fuera de rango
curl -s -X POST http://localhost:8044/registro.do \
     -d "nombre=Ana&email=ana@test.com&edad=200" | grep -o "entre"
# Esperado: entre

# edad no numérica
curl -s -X POST http://localhost:8044/registro.do \
     -d "nombre=Ana&email=ana@test.com&edad=abc" | grep -o "entero"
# Esperado: entero

# POST correcto
curl -s -X POST http://localhost:8044/registro.do \
     -d "nombre=Ana&email=ana%40test.com&edad=30" | grep -o "correcto"
# Esperado: correcto
```
