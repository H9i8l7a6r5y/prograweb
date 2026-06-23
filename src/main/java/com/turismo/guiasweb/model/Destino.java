package com.turismo.guiasweb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "destinos")
public class Destino {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String ciudad;
    private String descripcion;
    
    public Destino() {}
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}