package org.elvis.webbappcookiematu.services;

import org.elvis.webbappcookiematu.models.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> listar();
    Optional<Categoria> porId(Long id);
    void guardar(Categoria categoria);
    void eliminar(Long id);
}
