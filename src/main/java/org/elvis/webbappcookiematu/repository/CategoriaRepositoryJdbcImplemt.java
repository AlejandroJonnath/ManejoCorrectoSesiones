package org.elvis.webbappcookiematu.repository;

import org.elvis.webbappcookiematu.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositoryJdbcImplemt implements Repository<Categoria> {

    //Creamos una variable donde vamos a guarda la conexisón
    private Connection conn;

    public CategoriaRepositoryJdbcImplemt(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from categoria")) {
            while (rs.next()) {
                Categoria c = getCategoria(rs);
                categorias.add(c);
            }
        }
        return categorias;
    }


    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;
        try (PreparedStatement stmt = conn.prepareStatement(
                "select * from categoria where idcategoria=?")) {
            stmt.setLong(1, id); //1 ,2, 3 ,4
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    categoria=getCategoria(rs);
                }

            }

        }
        return categoria;
    }

    @Override
    public void guardar(Categoria categoria) throws SQLException {

        //Declaramos una variable de tipo String de nombre sql
        String sql;
        //Implemento un condicional para saber si el idcategoria es distinto y mayor
        if (categoria.getIdCategoria() != null && categoria.getIdCategoria()>0){
            sql = "update categoria set nombre=?, descripcion=? Where idcategoria=?";
        }else{
            sql="insert into categoria(nombre, descripcion, condicion)VALUES(?,?,1)";
        }
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            if (categoria.getIdCategoria()!=null && categoria.getIdCategoria()>0){
                stmt.setString(1, categoria.getNombre());
                stmt.setString(2, categoria.getDescripcion());
                stmt.setLong(3, categoria.getIdCategoria());
            }else{
                stmt.setString(1, categoria.getNombre());
                stmt.setString(2, categoria.getDescripcion());
            }



            //stmt.setInt(3, categoria.getCondicion());
            stmt.executeUpdate();
        }

    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "UPDATE categoria SET condicion = 0 WHERE idcategoria = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setNombre(rs.getString("nombre"));
        c.setDescripcion(rs.getString("descripcion"));
        c.setCondicion(rs.getInt("condicion"));
        c.setIdCategoria(rs.getLong("idCategoria"));
        return c;
    }


}
