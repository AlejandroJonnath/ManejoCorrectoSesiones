package org.elvis.webbappcookiematu.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elvis.webbappcookiematu.models.Productos;
import org.elvis.webbappcookiematu.services.LoginService;
import org.elvis.webbappcookiematu.services.LoginServiceSessionImplement;
import org.elvis.webbappcookiematu.services.ProductoService;
import org.elvis.webbappcookiematu.services.ProductoServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet ("/productos")
public class ProductosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Obtener la conexión
        Connection conn = (Connection) req.getAttribute("conn");

        // 2. Crear el servicio de productos
        ProductoService service = new ProductoServiceJdbcImplement(conn);

        // 3. Obtener la lista de productos
        List<Productos> productos = service.listar();

        // 4. Obtener el nombre de usuario autenticado
        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> userName = auth.getUserName(req);

        // 5. Setear atributos en el request
        req.setAttribute("productos", productos);
        req.setAttribute("username", userName);

        // 6. Redireccionar a la vista JSP
        getServletContext().getRequestDispatcher("/productoListar.jsp").forward(req, resp);
    }
}
