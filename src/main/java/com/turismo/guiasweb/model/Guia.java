
package com.turismo.guiasweb.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "guias")
public class Guia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    private String idioma;
    
    @Column(name = "tarifa_hora")
    private BigDecimal tarifaHora;
    
    private BigDecimal puntuacion;
    private Integer disponibilidad;
    
    // ========== NUEVO CAMPO FOTO ==========
    private String foto = "https://via.placeholder.com/150";
    
    @ManyToOne
    @JoinColumn(name = "id_destino")
    private Destino destino;
    
    // ========== CONSTRUCTORES ==========
    public Guia() {}
    
    public Guia(String nombre, String idioma, BigDecimal tarifaHora, BigDecimal puntuacion, Integer disponibilidad) {
        this.nombre = nombre;
        this.idioma = idioma;
        this.tarifaHora = tarifaHora;
        this.puntuacion = puntuacion;
        this.disponibilidad = disponibilidad;
    }
    
    // ========== GETTERS Y SETTERS ==========
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    
    public BigDecimal getTarifaHora() { return tarifaHora; }
    public void setTarifaHora(BigDecimal tarifaHora) { this.tarifaHora = tarifaHora; }
    
    public BigDecimal getPuntuacion() { return puntuacion; }
    public void setPuntuacion(BigDecimal puntuacion) { this.puntuacion = puntuacion; }
    
    public Integer getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(Integer disponibilidad) { this.disponibilidad = disponibilidad; }
    
    public Destino getDestino() { return destino; }
    public void setDestino(Destino destino) { this.destino = destino; }
    
    // ========== GETTER Y SETTER PARA FOTO ==========
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
    
    public boolean isDisponible() {
        return disponibilidad != null && disponibilidad == 1;
    }
}