<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Empleado — <c:out value="${empleado.nombre}"/></title>
    <style>
        body    { font-family: sans-serif; max-width: 540px; margin: 40px auto; color: #333; }
        h1      { border-bottom: 2px solid #2980b9; padding-bottom: 8px; }
        dl      { margin: 24px 0; }
        dt      { font-weight: bold; color: #888; font-size: 0.82em;
                  text-transform: uppercase; letter-spacing: 0.05em; margin-top: 18px; }
        dd      { margin: 4px 0 0 0; font-size: 1.05em; }
        .acciones { margin-top: 28px; display: flex; gap: 12px; align-items: center; flex-wrap: wrap; }
        .btn-editar { display: inline-block; padding: 9px 20px; background: #2980b9;
                      color: #fff; text-decoration: none; border-radius: 4px; }
        .btn-editar:hover { background: #2471a3; }
        .btn-elim { padding: 9px 20px; background: #e74c3c; color: #fff;
                    border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        .btn-elim:hover { background: #c0392b; }
        a.volver { color: #666; font-size: 0.95em; }
    </style>
</head>
<body>
    <h1>Empleado #<c:out value="${empleado.id}"/></h1>

    <dl>
        <dt>Nombre</dt>
        <dd><c:out value="${empleado.nombre}"/></dd>

        <dt>Departamento</dt>
        <dd><c:out value="${empleado.departamento}"/></dd>

        <dt>Salario</dt>
        <dd><fmt:formatNumber value="${empleado.salario}" minFractionDigits="2" maxFractionDigits="2"/> €</dd>
    </dl>

    <div class="acciones">
        <a class="btn-editar"
           href="${pageContext.request.contextPath}/empleados.do?method=editar&amp;id=${empleado.id}">Editar</a>

        <form method="post"
              action="${pageContext.request.contextPath}/empleados.do"
              onsubmit="return confirm('¿Eliminar a «${empleado.nombre}»?')">
            <input type="hidden" name="method" value="eliminar"/>
            <input type="hidden" name="id"     value="${empleado.id}"/>
            <button type="submit" class="btn-elim">Eliminar</button>
        </form>

        <a class="volver"
           href="${pageContext.request.contextPath}/empleados.do?method=listar">← Volver a la lista</a>
    </div>
</body>
</html>
