package com.turismo.guiasweb.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_guia")
    private Guia guia;
    
    private LocalDate fecha;
    private LocalTime hora;
    private int horasContratadas;
    private BigDecimal costoTotal;
    
    // ===== CONSTRUCTORES =====
    public Reserva() {}
    
    public Reserva(Usuario usuario, Guia guia, LocalDate fecha, LocalTime hora, 
                   int horasContratadas, BigDecimal costoTotal) {
        this.usuario = usuario;
        this.guia = guia;
        this.fecha = fecha;
        this.hora = hora;
        this.horasContratadas = horasContratadas;
        this.costoTotal = costoTotal;
    }
    
    // ===== GETTERS Y SETTERS =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public Guia getGuia() { return guia; }
    public void setGuia(Guia guia) { this.guia = guia; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    
    public int getHorasContratadas() { return horasContratadas; }
    public void setHorasContratadas(int horasContratadas) { this.horasContratadas = horasContratadas; }
    
    public BigDecimal getCostoTotal() { return costoTotal; }
    public void setCostoTotal(BigDecimal costoTotal) { this.costoTotal = costoTotal; }
}