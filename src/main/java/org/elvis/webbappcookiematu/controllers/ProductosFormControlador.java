package org.elvis.webbappcookiematu.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elvis.webbappcookiematu.models.Productos;
import org.elvis.webbappcookiematu.services.ProductoService;
import org.elvis.webbappcookiematu.services.ProductoServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/productos/form")
public class ProductosFormControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        Productos producto = new Productos();
        if (id > 0) {
            Optional<Productos> optionalProducto = service.porId(id);
            if (optionalProducto.isPresent()) {
                producto = optionalProducto.get();
            }
        }

        req.setAttribute("producto", producto);
        getServletContext().getRequestDispatcher("/formularioProductos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);

        Map<String, String> errores = new HashMap<>();

        Long idProducto;
        try {
            idProducto = Long.parseLong(req.getParameter("idProducto"));
        } catch (NumberFormatException e) {
            idProducto = 0L;
        }

        Long idCategoria;
        try {
            idCategoria = Long.parseLong(req.getParameter("idCategoria"));
            if (idCategoria <= 0) {
                errores.put("idCategoria", "Debe ingresar un ID de categoría válido.");
            }
        } catch (NumberFormatException e) {
            errores.put("idCategoria", "ID de categoría inválido.");
            idCategoria = 0L;
        }

        String codigo = req.getParameter("codigo");
        if (codigo == null || codigo.trim().isEmpty()) {
            errores.put("codigo", "El código es obligatorio.");
        }

        String nombre = req.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio.");
        }

        Long stock;
        try {
            stock = Long.parseLong(req.getParameter("stock"));
            if (stock < 0) {
                errores.put("stock", "El stock debe ser un número positivo.");
            }
        } catch (NumberFormatException e) {
            errores.put("stock", "Stock inválido.");
            stock = 0L;
        }

        String descripcion = req.getParameter("descripcion");
        if (descripcion == null || descripcion.trim().isEmpty()) {
            errores.put("descripcion", "La descripción es obligatoria.");
        }

        String imagen = req.getParameter("imagen"); // Opcional, no se valida por ahora

        boolean condicion = "true".equals(req.getParameter("condicion"));

        Productos producto = new Productos();
        producto.setIdProducto(idProducto);
        producto.setIdCategoria(idCategoria);
        producto.setCodigo(codigo);
        producto.setNombre(nombre);
        producto.setStock(stock);
        producto.setDescripcion(descripcion);
        producto.setImagen(imagen);
        producto.setCondicion(condicion);

        if (errores.isEmpty()) {
            service.guardar(producto);
            resp.sendRedirect(req.getContextPath() + "/productos");
        } else {
            req.setAttribute("errores", errores);
            req.setAttribute("producto", producto);
            getServletContext().getRequestDispatcher("/formularioProductos.jsp").forward(req, resp);
        }
    }
}
