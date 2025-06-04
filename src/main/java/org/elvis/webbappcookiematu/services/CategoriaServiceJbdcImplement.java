package org.elvis.webbappcookiematu.services;

import org.elvis.webbappcookiematu.models.Categoria;
import org.elvis.webbappcookiematu.repository.CategoriaRepositoryJdbcImplemt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoriaServiceJbdcImplement implements CategoriaService {

    //Creamos una variable de tipo CategoriaRepositoryJdbcImplmet
    //creamos una variable de tipo COnnection
    private CategoriaRepositoryJdbcImplemt repositoryJdbc;
    public CategoriaServiceJbdcImplement(Connection conn){
        this.repositoryJdbc = new CategoriaRepositoryJdbcImplemt(conn);
    }

    @Override
    public List<Categoria> listar() {
        try{
            return repositoryJdbc.listar();
        }catch(SQLException throwables){
            throw  new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Categoria> porId(Long id) {
        try{
            return Optional.ofNullable(repositoryJdbc.porId(id));
        }catch(SQLException throwables){
            throw  new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(Categoria categoria) {
        try{
            repositoryJdbc.guardar(categoria);
        }catch(SQLException throwables){
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            repositoryJdbc.eliminar(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
