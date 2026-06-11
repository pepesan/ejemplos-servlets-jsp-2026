<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de candidato — Struts</title>
    <style>
        *, *::before, *::after { box-sizing: border-box; }
        body   { font-family: sans-serif; max-width: 700px; margin: 40px auto;
                 color: #222; padding: 0 16px; background: #fafafa; }
        h1     { border-bottom: 3px solid #2980b9; padding-bottom: 10px; color: #1a5276; }
        h2     { color: #2980b9; margin: 28px 0 10px; font-size: 1.05em;
                 text-transform: uppercase; letter-spacing: .05em; }
        .grupo { background: #fff; border: 1px solid #d5d8dc; border-radius: 6px;
                 padding: 18px 20px; margin-bottom: 14px; }
        label  { display: block; font-weight: bold; margin-bottom: 4px; font-size: .93em; }
        .hint  { font-weight: normal; color: #777; font-size: .87em; margin-left: 4px; }
        input[type=text], input[type=email], input[type=date], textarea, select {
            width: 100%; padding: 8px 10px; border: 1px solid #aab7c4; border-radius: 4px;
            font-size: .97em; margin-top: 2px; background: #fff; }
        input[type=text]:focus, input[type=email]:focus,
        input[type=date]:focus, textarea:focus, select:focus {
            outline: none; border-color: #2980b9; box-shadow: 0 0 0 2px rgba(41,128,185,.2); }
        .radio-grupo, .check-grupo {
            display: flex; flex-wrap: wrap; gap: 10px 24px; margin-top: 6px; }
        .radio-grupo label, .check-grupo label {
            font-weight: normal; display: flex; align-items: center; gap: 6px;
            cursor: pointer; }
        .radio-grupo input, .check-grupo input { width: auto; margin: 0; }
        textarea { resize: vertical; min-height: 90px; }
        .error-campo { color: #c0392b; font-size: .85em; margin-top: 4px;
                       display: block; }
        .errores-resumen { background: #fdecea; border-left: 4px solid #e74c3c;
                           padding: 12px 16px; border-radius: 4px; margin-bottom: 20px; }
        .errores-resumen ul { margin: 6px 0 0; padding-left: 20px; }
        .errores-resumen li { color: #c0392b; margin: 3px 0; }
        .btn-submit { display: block; width: 100%; padding: 12px;
                      background: #2980b9; color: #fff; border: none;
                      border-radius: 5px; font-size: 1.05em; cursor: pointer;
                      margin-top: 20px; }
        .btn-submit:hover { background: #2471a3; }
        .nota-tecnica { background: #eaf4fc; border-left: 4px solid #85c1e9;
                        padding: 10px 14px; border-radius: 4px; margin-top: 8px;
                        font-size: .85em; color: #1a5276; }
    </style>
</head>
<body>

<h1>Registro de candidato</h1>

<%-- Resumen de errores si hay alguno — html:errors es un tag vacío (no admite body) --%>
<html:errors/>

<html:form action="/registrar.do" method="post">

    <%-- ══════════════════════════════════════════════════════════
         SECCIÓN 1: Datos personales
         html:text → <input type="text">
         html:errors property="x" → error asociado al campo
         ══════════════════════════════════════════════════════════ --%>
    <h2>Datos personales</h2>

    <div class="grupo">
        <label for="nombre">Nombre *</label>
        <html:text property="nombre" styleId="nombre" styleClass=""/>
        <span class="error-campo"><html:errors property="nombre"/></span>
    </div>

    <div class="grupo">
        <label for="apellidos">Apellidos *</label>
        <html:text property="apellidos" styleId="apellidos"/>
        <span class="error-campo"><html:errors property="apellidos"/></span>
    </div>

    <div class="grupo">
        <label for="email">Email *</label>
        <html:text property="email" styleId="email"/>
        <span class="error-campo"><html:errors property="email"/></span>
    </div>

    <%-- CAMPO FECHA: Struts 1.x no tiene <html:date>.
         Usamos <input type="date"> de HTML5 con name= igual al campo del ActionForm.
         Struts lo leerá como cualquier parámetro POST y poblará fechaNacimiento.
         El value EL repopula el campo en caso de error de validación.
    --%>
    <div class="grupo">
        <label for="fechaNacimiento">Fecha de nacimiento *</label>
        <input type="date" id="fechaNacimiento" name="fechaNacimiento"
               value="${registroForm.fechaNacimiento}">
        <span class="error-campo"><html:errors property="fechaNacimiento"/></span>
        <div class="nota-tecnica">
            Struts 1.x no incluye <code>&lt;html:date&gt;</code>: se usa
            <code>&lt;input type="date"&gt;</code> nativo. Struts popula el
            campo del ActionForm a partir del nombre del parámetro POST.
        </div>
    </div>

    <%-- ══════════════════════════════════════════════════════════
         SECCIÓN 2: Radio buttons
         html:radio property="genero" value="m" → <input type="radio" name="genero" value="m">
         Struts marca automáticamente el radio cuyo value coincide con el campo del form.
         ══════════════════════════════════════════════════════════ --%>
    <h2>Género</h2>

    <div class="grupo">
        <label>Género *</label>
        <div class="radio-grupo">
            <label><html:radio property="genero" value="m"/> Hombre</label>
            <label><html:radio property="genero" value="f"/> Mujer</label>
            <label><html:radio property="genero" value="o"/> Otro</label>
            <label><html:radio property="genero" value="p"/> Prefiero no decir</label>
        </div>
        <span class="error-campo"><html:errors property="genero"/></span>
    </div>

    <%-- ══════════════════════════════════════════════════════════
         SECCIÓN 3: Select
         html:select + html:option / html:options
         Struts selecciona automáticamente la opción cuyo value coincide con el campo.
         ══════════════════════════════════════════════════════════ --%>
    <h2>Ubicación y experiencia</h2>

    <div class="grupo">
        <label for="pais">País *</label>
        <html:select property="pais" styleId="pais">
            <html:option value="">— Selecciona un país —</html:option>
            <html:option value="es">🇪🇸 España</html:option>
            <html:option value="mx">🇲🇽 México</html:option>
            <html:option value="ar">🇦🇷 Argentina</html:option>
            <html:option value="co">🇨🇴 Colombia</html:option>
            <html:option value="cl">🇨🇱 Chile</html:option>
            <html:option value="pe">🇵🇪 Perú</html:option>
            <html:option value="ve">🇻🇪 Venezuela</html:option>
            <html:option value="ec">🇪🇨 Ecuador</html:option>
            <html:option value="bo">🇧🇴 Bolivia</html:option>
            <html:option value="uy">🇺🇾 Uruguay</html:option>
            <html:option value="py">🇵🇾 Paraguay</html:option>
            <html:option value="cr">🇨🇷 Costa Rica</html:option>
            <html:option value="gt">🇬🇹 Guatemala</html:option>
            <html:option value="cu">🇨🇺 Cuba</html:option>
            <html:option value="otro">🌍 Otro</html:option>
        </html:select>
        <span class="error-campo"><html:errors property="pais"/></span>
    </div>

    <div class="grupo">
        <label for="nivel">Nivel de experiencia *</label>
        <html:select property="nivel" styleId="nivel">
            <html:option value="">— Selecciona un nivel —</html:option>
            <html:option value="junior">Junior (0–2 años)</html:option>
            <html:option value="mid">Mid (2–5 años)</html:option>
            <html:option value="senior">Senior (+5 años)</html:option>
        </html:select>
        <span class="error-campo"><html:errors property="nivel"/></span>
    </div>

    <%-- ══════════════════════════════════════════════════════════
         SECCIÓN 4: Checkboxes múltiples
         html:multibox → para String[] en el ActionForm.
         Cada <html:multibox> con el mismo property pero distinto value genera
         un checkbox independiente. Struts popula el array con todos los values marcados.

         IMPORTANTE: ActionForm.reset() debe poner tecnologias = new String[0]
         para que las desmarcadas desaparezcan del array en cada petición.
         ══════════════════════════════════════════════════════════ --%>
    <h2>Tecnologías</h2>

    <div class="grupo">
        <label>Tecnologías que dominas * <span class="hint">(marca todas las que apliquen)</span></label>
        <div class="check-grupo">
            <label><html:multibox property="tecnologias" value="java"/>    Java</label>
            <label><html:multibox property="tecnologias" value="python"/>  Python</label>
            <label><html:multibox property="tecnologias" value="js"/>      JavaScript</label>
            <label><html:multibox property="tecnologias" value="ts"/>      TypeScript</label>
            <label><html:multibox property="tecnologias" value="go"/>      Go</label>
            <label><html:multibox property="tecnologias" value="rust"/>    Rust</label>
            <label><html:multibox property="tecnologias" value="php"/>     PHP</label>
            <label><html:multibox property="tecnologias" value="ruby"/>    Ruby</label>
            <label><html:multibox property="tecnologias" value="csharp"/>  C#</label>
            <label><html:multibox property="tecnologias" value="sql"/>     SQL</label>
            <label><html:multibox property="tecnologias" value="nosql"/>   NoSQL</label>
            <label><html:multibox property="tecnologias" value="devops"/>  DevOps</label>
        </div>
        <span class="error-campo"><html:errors property="tecnologias"/></span>
    </div>

    <%-- ══════════════════════════════════════════════════════════
         SECCIÓN 5: Más radios + textarea
         ══════════════════════════════════════════════════════════ --%>
    <h2>Preferencias de trabajo</h2>

    <div class="grupo">
        <label>Modalidad de trabajo *</label>
        <div class="radio-grupo">
            <label><html:radio property="modalidad" value="presencial"/> Presencial</label>
            <label><html:radio property="modalidad" value="remoto"/>     Remoto</label>
            <label><html:radio property="modalidad" value="hibrido"/>    Híbrido</label>
        </div>
        <span class="error-campo"><html:errors property="modalidad"/></span>
    </div>

    <%-- TEXTAREA: html:textarea → <textarea name="comentarios" rows="5"></textarea>
         El contenido del textarea va entre las etiquetas (no en value).
         Struts popula el cuerpo con el valor actual del campo al re-mostrar el form.
    --%>
    <div class="grupo">
        <label for="comentarios">
            Comentarios adicionales
            <span class="hint">(opcional, máx. 500 caracteres)</span>
        </label>
        <html:textarea property="comentarios" styleId="comentarios" rows="5"/>
        <span class="error-campo"><html:errors property="comentarios"/></span>
    </div>

    <%-- SEGUNDA FECHA: disponibilidad — opcional --%>
    <div class="grupo">
        <label for="fechaDisponible">
            Fecha de disponibilidad
            <span class="hint">(opcional — a partir de cuándo puedes incorporarte)</span>
        </label>
        <input type="date" id="fechaDisponible" name="fechaDisponible"
               value="${registroForm.fechaDisponible}">
        <span class="error-campo"><html:errors property="fechaDisponible"/></span>
    </div>

    <input type="submit" class="btn-submit" value="Registrar candidatura →">

</html:form>

<p style="margin-top:20px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
