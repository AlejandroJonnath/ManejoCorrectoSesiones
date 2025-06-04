<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, org.elvis.webbappcookiematu.models.*" %>
<%
    List<Productos> productos = (List<Productos>) request.getAttribute("productos");
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>
<html>
<head>
    <title>Listado Productos</title>
</head>
<body>

<h1>Listado Producto</h1>
<%
    if (username.isPresent()) { %>
<div style="color: blue;">Hola, <%= username.get() %> bienvenido</div>
<div><a href="<%= request.getContextPath() %>/productos/form">Añadir Producto</a></div>
<% } %>

<table border="1" cellpadding="5" cellspacing="0">
    <thead>
    <tr>
        <th>ID Producto</th>
        <th>ID Categoria</th>
        <th>Código</th>
        <th>Nombre</th>
        <th>Stock</th>
        <th>Descripción</th>
        <th>Imagen</th>
        <th>Condición</th>
        <% if (username.isPresent()) { %>
        <th>Acción</th>
        <% } %>
    </tr>
    </thead>
    <tbody>
    <%
        for (Productos prod : productos) { %>
    <tr>
        <td><%= prod.getIdProducto() %></td>
        <td><%= prod.getIdCategoria() %></td>
        <td><%= prod.getCodigo() %></td>
        <td><%= prod.getNombre() %></td>
        <td><%= prod.getStock() %></td>
        <td><%= prod.getDescripcion() %></td>
        <td>
            <% if(prod.getImagen() != null && !prod.getImagen().isEmpty()) { %>
            <img src="<%= prod.getImagen() %>" alt="Imagen" width="50" height="50"/>
            <% } else { %>
            Sin imagen
            <% } %>
        </td>
        <td><%= prod.isCondicion() ? "Activo" : "Inactivo" %></td>
        <% if (username.isPresent()) { %>
        <td>
            <a href="<%= request.getContextPath() %>/productos/form?id=<%= prod.getIdProducto() %>">Editar</a>
            <!-- Puedes agregar aquí el enlace para eliminar -->
            <a href="#">Eliminar</a>
        </td>
        <% } %>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
