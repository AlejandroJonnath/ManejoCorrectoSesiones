package org.elvis.webbappcookiematu.models;

public class Productos {
    private long idProducto;
    private long idCategoria;
    private String codigo;
    private String nombre;
    private long stock;
    private String descripcion;
    private String imagen;
    private boolean condicion;

    public Productos() {

    }

    public Productos(long idProducto, long idCategoria, String codigo, String nombre, long stock, String descripcion, String imagen, boolean condicion) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.condicion = condicion;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }
}
