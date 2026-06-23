package com.turismo.guiasweb.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, Integer> {
    
    @Query("SELECT g FROM Guia g WHERE g.disponibilidad = 1 AND g.destino.id = :idDestino")
    List<Guia> findGuiasPorDestino(@Param("idDestino") int idDestino);
}