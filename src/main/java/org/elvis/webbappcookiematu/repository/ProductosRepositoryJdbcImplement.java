package org.elvis.webbappcookiematu.repository;

import org.elvis.webbappcookiematu.models.Productos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosRepositoryJdbcImplement implements Repository<Productos> {

    private Connection conn;

    public ProductosRepositoryJdbcImplement(Connection connection) {
        this.conn = connection;
    }

    @Override
    public List<Productos> listar() throws SQLException {
        List<Productos> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM articulo WHERE condicion = 1")) {
            while (rs.next()) {
                Productos p = getProductos(rs);
                productos.add(p);
            }
        }
        return productos;
    }

    @Override
    public Productos porId(Long id) throws SQLException {
        Productos producto = null;
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM articulo WHERE idArticulo = ? AND condicion = 1")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = getProductos(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardar(Productos producto) throws SQLException {
        String sql;
        boolean esActualizacion = producto.getIdProducto() > 0;

        if (esActualizacion) {
            sql = "UPDATE articulo SET idCategoria = ?, codigo = ?, nombre = ?, stock = ?, descripcion = ?, imagen = ? WHERE idArticulo = ?";
        } else {
            sql = "INSERT INTO articulo(idCategoria, codigo, nombre, stock, descripcion, imagen, condicion) VALUES (?, ?, ?, ?, ?, ?, 1)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, producto.getIdCategoria());
            stmt.setString(2, producto.getCodigo());
            stmt.setString(3, producto.getNombre());
            stmt.setLong(4, producto.getStock());
            stmt.setString(5, producto.getDescripcion());
            stmt.setString(6, producto.getImagen());

            if (esActualizacion) {
                stmt.setLong(7, producto.getIdProducto());
            }

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "UPDATE articulo SET condicion = 0 WHERE idArticulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Productos getProductos(ResultSet rs) throws SQLException {
        Productos p = new Productos();
        p.setIdProducto(rs.getLong("idArticulo"));
        p.setIdCategoria(rs.getLong("idCategoria"));
        p.setCodigo(rs.getString("codigo"));
        p.setNombre(rs.getString("nombre"));
        p.setStock(rs.getLong("stock"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setImagen(rs.getString("imagen"));
        p.setCondicion(rs.getInt("condicion") == 1); // Convertimos de int a boolean
        return p;
    }
}
