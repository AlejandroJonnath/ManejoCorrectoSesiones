<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, org.elvis.webbappcookiematu.models.*" %>
<%
  Productos producto = (Productos) request.getAttribute("producto");
  Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
  // "categorias" viene del servlet como List<Categoria>
  List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
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

<form action="<%=request.getContextPath()%>/productos/form" method="post">
  <!-- ID oculto para edición -->
  <input type="hidden" name="idProducto"
         value="<%= producto.getIdProducto() != 0 ? producto.getIdProducto() : "" %>">

  <!-- SELECT para elegir la categoría -->
  <div>
    <label for="idCategoria">Categoría</label>
    <select id="idCategoria" name="idCategoria">
      <option value="">-- Seleccione una categoría --</option>
      <% for (Categoria cat : categorias) {
        long cId = cat.getIdCategoria();
        boolean selected = (producto.getIdCategoria() == cId);
      %>
      <option value="<%= cId %>" <%= selected ? "selected" : "" %>>
        <%= cat.getNombre() %>
      </option>
      <% } %>
    </select>
    <div style="color: red;">
      <%= (errores != null && errores.get("idCategoria") != null) ? errores.get("idCategoria") : "" %>
    </div>
  </div>

  <!-- Campo "Código" -->
  <div>
    <label for="codigo">Código</label>
    <input type="text" id="codigo" name="codigo"
           value="<%= producto.getCodigo() != null ? producto.getCodigo() : "" %>">
    <div style="color: red;">
      <%= (errores != null && errores.get("codigo") != null) ? errores.get("codigo") : "" %>
    </div>
  </div>

  <!-- Campo "Nombre" -->
  <div>
    <label for="nombre">Nombre</label>
    <input type="text" id="nombre" name="nombre"
           value="<%= producto.getNombre() != null ? producto.getNombre() : "" %>">
    <div style="color: red;">
      <%= (errores != null && errores.get("nombre") != null) ? errores.get("nombre") : "" %>
    </div>
  </div>

  <!-- Campo "Stock" -->
  <div>
    <label for="stock">Stock</label>
    <input type="number" id="stock" name="stock"
           value="<%= producto.getStock() != 0 ? producto.getStock() : "" %>">
    <div style="color: red;">
      <%= (errores != null && errores.get("stock") != null) ? errores.get("stock") : "" %>
    </div>
  </div>

  <!-- Campo "Descripción" -->
  <div>
    <label for="descripcion">Descripción</label>
    <input type="text" id="descripcion" name="descripcion"
           value="<%= producto.getDescripcion() != null ? producto.getDescripcion() : "" %>">
    <div style="color: red;">
      <%= (errores != null && errores.get("descripcion") != null) ? errores.get("descripcion") : "" %>
    </div>
  </div>

  <!-- Campo "Imagen (URL)" -->
  <div>
    <label for="imagen">Imagen (URL)</label>
    <input type="text" id="imagen" name="imagen"
           value="<%= producto.getImagen() != null ? producto.getImagen() : "" %>">
  </div>

  <!-- Campo "Condición" -->
  <div>
    <label for="condicion">Condición</label>
    <select id="condicion" name="condicion">
      <option value="true"  <%= producto.isCondicion() ? "selected" : "" %>>Activo</option>
      <option value="false" <%= !producto.isCondicion() ? "selected" : "" %>>Inactivo</option>
    </select>
  </div>

  <!-- Botón Crear / Editar -->
  <div>
    <input type="submit"
           value="<%= (producto.getIdProducto() != 0) ? "Editar" : "Crear" %>">
  </div>
</form>
</body>
</html>
