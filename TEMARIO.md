# Servlets y JSP + MVC Clásico

## Duración

28 Horas.

## Objetivos

- Entender la arquitectura fundamental de las aplicaciones web Java (el ciclo de vida del Servlet) para depurar errores de ejecución en servidores de aplicaciones antiguos.
- Adquirir la capacidad de leer, mantener y sanear páginas JSP que contienen lógica de negocio incrustada (scriptlets).
- Analizar arquitecturas basadas en el patrón MVC legacy, específicamente comprendiendo el flujo de ejecución en Struts 1.x (Actions y Forms).
- Mantener y depurar la capa de persistencia basada en versiones clásicas de Hibernate (configuración XML y mapeos hbm).

## Contenidos

### 0. Manejo de Maven para el manejo de dependencias

- Instalación de Maven
- Ciclo de vida de un proyecto Maven
- Control básico de paquetes y dependencias

### 1. Ciclo de Vida del Servlet

- Arquitectura del contenedor Servlet (Tomcat/WebLogic).
- Métodos clave: `init()`, `service()` (`doGet`/`doPost`) y `destroy()`.
- Mapeo y configuración del Servlet (vía `web.xml` y anotaciones de Java 8).
- Manejo de objetos de solicitud (`HttpServletRequest`) y respuesta (`HttpServletResponse`).

### 2. JavaServer Pages (JSP) y JSTL

- Elementos de una JSP (directives, declarations, scriptlets).
- Objetos implícitos de JSP (`request`, `session`, `application`).
- Uso de la JSTL (JavaServer Pages Standard Tag Library) para lógica de presentación.
- Inclusión y reenvío de páginas.

### 3. Patrón MVC Clásico (Front Controller)

- Definición y beneficios del patrón Model-View-Controller (MVC).
- Implementación del patrón Front Controller con un Servlet genérico.
- Paso de datos entre capas (Model beans).
- Ejemplo práctico de una aplicación web simple con Servlets/JSP/MVC.

### 4. Struts 1.x: Acciones y Validaciones

- Arquitectura y flujo de peticiones en Struts 1.x.
- Configuración central: `struts-config.xml`.
- Mantenimiento de `ActionForm`s (manejo de datos del formulario).
- Implementación y depuración de `Action` (lógica de negocio).
- Mecanismos de validación clásica de Struts.

### 5. Hibernate Clásico: ORM y Mapping

- Introducción a la tecnología ORM y los beneficios de Hibernate.
- Configuración de Hibernate (vía XML o anotaciones clásicas).
- Mapeo de entidades (`hbm.xml` o anotaciones).
- Manejo de sesiones, transacciones y el ciclo de vida de los objetos persistentes.
- Consultas con HQL (Hibernate Query Language).
