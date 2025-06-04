package org.elvis.webbappcookiematu.services;

import org.elvis.webbappcookiematu.models.Productos;
import org.elvis.webbappcookiematu.repository.ProductosRepositoryJdbcImplement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoServiceJdbcImplement implements ProductoService {

    // Creamos la instancia del repositorio
    private ProductosRepositoryJdbcImplement repositoryJdbc;

    // Constructor que recibe la conexión y crea el repositorio
    public ProductoServiceJdbcImplement(Connection conn) {
        this.repositoryJdbc = new ProductosRepositoryJdbcImplement(conn);
    }

    @Override
    public List<Productos> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Productos> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Productos producto) {
        try {
            repositoryJdbc.guardar(producto);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {

        try {

            repositoryJdbc.eliminar(id);

        } catch (SQLException e) {

            throw new ServiceJdbcException(e.getMessage(), e.getCause());

        }
    }
}
