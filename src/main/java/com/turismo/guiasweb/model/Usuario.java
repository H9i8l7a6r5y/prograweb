package com.turismo.guiasweb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    // ========== NUEVO CAMPO ==========
    @Column(name = "rol", nullable = false)
    private String rol = "USER";  // Valor por defecto: USER
    
    // ========== CONSTRUCTORES ==========
    
    public Usuario() {}
    
    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = "USER";  // Por defecto
    }
    
    public Usuario(String nombre, String email, String password, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    // ========== GETTER Y SETTER PARA ROL ==========
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}