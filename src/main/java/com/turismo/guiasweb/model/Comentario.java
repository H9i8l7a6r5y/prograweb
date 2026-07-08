package com.turismo.guiasweb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_guia")
    private Integer idGuia;
    
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Column(columnDefinition = "TEXT")
    private String comentario;
    
    private LocalDateTime fecha;
    
    @Transient
    private String nombreUsuario;
    
    // ========== CONSTRUCTORES ==========
    public Comentario() {}
    
    public Comentario(Integer idGuia, Integer idUsuario, String comentario) {
        this.idGuia = idGuia;
        this.idUsuario = idUsuario;
        this.comentario = comentario;
        this.fecha = LocalDateTime.now();
    }
    
    // ========== GETTERS Y SETTERS ==========
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getIdGuia() { return idGuia; }
    public void setIdGuia(Integer idGuia) { this.idGuia = idGuia; }
    
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}