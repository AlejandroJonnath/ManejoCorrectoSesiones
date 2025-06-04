<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, org.elvis.webbappcookiematu.models.*" %>
<%
  Productos producto = (Productos) request.getAttribute("producto");
  Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
%>
<html>
<head>
  <title>Formulario Producto</title>
</head>
<body>
<h1>Formulario Producto</h1>

<% if (errores != null && !errores.isEmpty()) { %>
<div style="color: red; font-weight: bold;">
  Por favor corrige los errores en el formulario.
</div>
<% } %>

<div>
  <form action="<%=request.getContextPath()%>/productos/form" method="post">
    <!-- ID oculto para edición -->
    <input type="hidden" name="idProducto"
           value="<%=producto.getIdProducto() != 0 ? producto.getIdProducto() : ""%>">

    <!-- ID Categoría como campo oculto o visible -->
    <div>
      <input type="hidden" name="idCategoria"
             value="<%=producto.getIdCategoria() != 0 ? producto.getIdCategoria() : ""%>">
    </div>

    <div>
      <label for="codigo">Código</label>
      <input type="text" id="codigo" name="codigo"
             value="<%= (producto.getCodigo() != null && !producto.getCodigo().isEmpty()) ? producto.getCodigo() : "" %>">
      <div style="color: red;">
        <%= (errores != null && errores.get("codigo") != null) ? errores.get("codigo") : "" %>
      </div>
    </div>

    <div>
      <label for="nombre">Nombre</label>
      <input type="text" id="nombre" name="nombre"
             value="<%=producto.getNombre() != null ? producto.getNombre() : ""%>">
      <div style="color: red;">
        <%= (errores != null && errores.get("nombre") != null) ? errores.get("nombre") : "" %>
      </div>
    </div>

    <div>
      <label for="stock">Stock</label>
      <input type="number" id="stock" name="stock"
             value="<%=producto.getStock() != 0 ? producto.getStock() : ""%>">
      <div style="color: red;">
        <%= (errores != null && errores.get("stock") != null) ? errores.get("stock") : "" %>
      </div>
    </div>

    <div>
      <label for="descripcion">Descripción</label>
      <input type="text" id="descripcion" name="descripcion"
             value="<%=producto.getDescripcion() != null ? producto.getDescripcion() : ""%>">
      <div style="color: red;">
        <%= (errores != null && errores.get("descripcion") != null) ? errores.get("descripcion") : "" %>
      </div>
    </div>

    <div>
      <label for="imagen">Imagen (URL)</label>
      <input type="text" id="imagen" name="imagen"
             value="<%=producto.getImagen() != null ? producto.getImagen() : ""%>">
    </div>

    <div>
      <label for="condicion">Condición</label>
      <select id="condicion" name="condicion">
        <option value="true" <%=producto.isCondicion() ? "selected" : "" %>>Activo</option>
        <option value="false" <%=!producto.isCondicion() ? "selected" : "" %>>Inactivo</option>
      </select>
    </div>

    <div>
      <input type="submit" value="<%=(producto.getIdProducto() != 0) ? "Editar" : "Crear"%>">
    </div>
  </form>
</div>
</body>
</html>
