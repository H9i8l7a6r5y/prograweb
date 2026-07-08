package com.turismo.guiasweb.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    
    // Buscar reservas por usuario
    List<Reserva> findByUsuarioId(int usuarioId);
    
    // Buscar reservas por guía y fecha (para contar)
    List<Reserva> findByGuiaIdAndFecha(int guiaId, LocalDate fecha);
    
    // Buscar reservas por guía, fecha y hora (para verificar duplicados)
    @Query("SELECT r FROM Reserva r WHERE r.guia.id = :guiaId AND r.fecha = :fecha AND r.hora = :hora")
    List<Reserva> findByGuiaIdAndFechaAndHora(
        @Param("guiaId") int guiaId,
        @Param("fecha") LocalDate fecha,
        @Param("hora") LocalTime hora
    );
    
    // Contar reservas de un guía en una fecha (para límite diario)
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.guia.id = :guiaId AND r.fecha = :fecha")
    int countByGuiaIdAndFecha(@Param("guiaId") int guiaId, @Param("fecha") LocalDate fecha);
}