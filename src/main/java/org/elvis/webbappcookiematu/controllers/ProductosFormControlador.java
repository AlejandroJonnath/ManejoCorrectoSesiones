package org.elvis.webbappcookiematu.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elvis.webbappcookiematu.models.Categoria;
import org.elvis.webbappcookiematu.models.Productos;
import org.elvis.webbappcookiematu.services.CategoriaService;
import org.elvis.webbappcookiematu.services.CategoriaServiceJbdcImplement;
import org.elvis.webbappcookiematu.services.ProductoService;
import org.elvis.webbappcookiematu.services.ProductoServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/productos/form")
public class ProductosFormControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService productoService = new ProductoServiceJdbcImplement(conn);
        CategoriaService categoriaService = new CategoriaServiceJbdcImplement(conn);

        // 1) Obtener ID de producto (si viene en url ?id=xxx)
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        // 2) Si es edición, cargar producto; si no, usar uno vacío
        Productos producto = new Productos();
        if (id > 0) {
            Optional<Productos> opt = productoService.porId(id);
            if (opt.isPresent()) {
                producto = opt.get();
            }
        }

        // 3) Listar todas las categorías para el <select>
        List<Categoria> categorias = categoriaService.listar();
        req.setAttribute("categorias", categorias);

        // 4) Poner el producto (nuevo o cargado) en request
        req.setAttribute("producto", producto);
        getServletContext().getRequestDispatcher("/formularioProductos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService productoService = new ProductoServiceJdbcImplement(conn);
        CategoriaService categoriaService = new CategoriaServiceJbdcImplement(conn);

        Map<String, String> errores = new HashMap<>();

        // 1) Leer idProducto (oculto), si viene vacío: 0
        Long idProducto;
        try {
            idProducto = Long.parseLong(req.getParameter("idProducto"));
        } catch (NumberFormatException e) {
            idProducto = 0L;
        }

        // 2) Leer idCategoria desde el <select>
        String strCategoria = req.getParameter("idCategoria");
        Long idCategoria = 0L;
        if (strCategoria == null || strCategoria.trim().isEmpty()) {
            errores.put("idCategoria", "Debe seleccionar una categoría.");
        } else {
            try {
                idCategoria = Long.parseLong(strCategoria);
            } catch (NumberFormatException e) {
                errores.put("idCategoria", "Formato de categoría inválido.");
            }
        }

        // 3) Validar código
        String codigo = req.getParameter("codigo");
        if (codigo == null || codigo.trim().isEmpty()) {
            errores.put("codigo", "El código es obligatorio.");
        }

        // 4) Validar nombre
        String nombre = req.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio.");
        }

        // 5) Validar stock
        Long stock = 0L;
        try {
            stock = Long.parseLong(req.getParameter("stock"));
            if (stock < 0) {
                errores.put("stock", "El stock debe ser un número positivo.");
            }
        } catch (NumberFormatException e) {
            errores.put("stock", "Stock inválido.");
        }

        // 6) Validar descripción
        String descripcion = req.getParameter("descripcion");
        if (descripcion == null || descripcion.trim().isEmpty()) {
            errores.put("descripcion", "La descripción es obligatoria.");
        }

        // 7) Leer imagen (opcional)
        String imagen = req.getParameter("imagen");

        // 8) Leer condición
        boolean condicion = "true".equals(req.getParameter("condicion"));

        // 9) Construir objeto productos con los valores recibidos
        Productos producto = new Productos();
        producto.setIdProducto(idProducto);
        producto.setIdCategoria(idCategoria);
        producto.setCodigo(codigo);
        producto.setNombre(nombre);
        producto.setStock(stock);
        producto.setDescripcion(descripcion);
        producto.setImagen(imagen);
        producto.setCondicion(condicion);

        // 10) Si no hay errores, guardar y redirigir; si hay, re-renderizar con errores y lista de categorías
        if (errores.isEmpty()) {
            productoService.guardar(producto);
            resp.sendRedirect(req.getContextPath() + "/productos");
        } else {
            // Volver a cargar las categorías para el <select>
            List<Categoria> categorias = categoriaService.listar();
            req.setAttribute("categorias", categorias);

            // Reenviar los errores y el producto para que el JSP los muestre
            req.setAttribute("errores", errores);
            req.setAttribute("producto", producto);
            getServletContext().getRequestDispatcher("/formularioProductos.jsp").forward(req, resp);
        }
    }
}
