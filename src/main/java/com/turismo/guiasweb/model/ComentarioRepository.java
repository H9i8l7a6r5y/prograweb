package com.turismo.guiasweb.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    
    List<Comentario> findByIdGuiaOrderByFechaDesc(Integer idGuia);
    
    @Query("SELECT c, u.nombre FROM Comentario c JOIN Usuario u ON c.idUsuario = u.id WHERE c.idGuia = :idGuia ORDER BY c.fecha DESC")
    List<Object[]> findComentariosConUsuario(@Param("idGuia") Integer idGuia);
}