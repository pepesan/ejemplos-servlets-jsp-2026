# 45 — Struts 1.x: Gestión de excepciones

**Puerto:** 8045  
**URL:** http://localhost:8045/

## Qué enseña

Manejo declarativo de excepciones en Struts 1.x usando `<global-exceptions>` y `<exception>` local en la action.

| Mecanismo | Ámbito | Prioridad |
|-----------|--------|-----------|
| `<exception>` dentro de `<action>` | Solo esa action | Mayor |
| `<global-exceptions>` | Todas las actions | Menor |

La resolución es: primero clase exacta, luego superclases; local antes que global.

**Requisito:** `<controller processorClass="org.apache.struts.action.RequestProcessor"/>` — el `ComposableRequestProcessor` por defecto de Struts 1.3 no enruta excepciones a `<global-exceptions>`.

## Escenarios

| URL | Excepción | Handler | Vista |
|-----|-----------|---------|-------|
| `/producto.do` | ninguna | — | `resultado.jsp` |
| `/producto.do?tipo=notfound` | `RecursoNoEncontradoException` | `LoggingExceptionHandler` (global) | `noEncontrado.jsp` |
| `/producto.do?tipo=division` | `ArithmeticException` | `ExceptionHandler` (local) | `errorCalculo.jsp` |
| `/producto.do?tipo=general` | `RuntimeException` | `ExceptionHandler` (global) | `error.jsp` |

## Pruebas curl

```bash
# Caso sin excepción
curl http://localhost:8045/producto.do

# RecursoNoEncontradoException → noEncontrado.jsp (global, handler personalizado)
curl http://localhost:8045/producto.do?tipo=notfound

# ArithmeticException → errorCalculo.jsp (local, sobreescribe global de RuntimeException)
curl http://localhost:8045/producto.do?tipo=division

# RuntimeException → error.jsp (global, handler por defecto)
curl http://localhost:8045/producto.do?tipo=general
```

## Arrancar / parar

```bash
./start.sh   # http://localhost:8045/
./stop.sh
./build.sh   # mvn clean package
./test.sh    # mvn test
```
