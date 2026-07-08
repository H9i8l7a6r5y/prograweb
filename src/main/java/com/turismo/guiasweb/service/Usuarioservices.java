package com.turismo.guiasweb.service;

import com.turismo.guiasweb.model.Usuario;
import com.turismo.guiasweb.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class Usuarioservices {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // 🔹 MÉTODO LOGIN
    public Usuario login(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmailAndPassword(email, password);
        return usuario.orElse(null);
    }
    
    // 🔹 MÉTODO REGISTRAR (ACTUALIZADO)
    public boolean registrar(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            return false;
        }
        
        // ✅ Si no tiene rol, asignar USER por defecto
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER");
        }
        
        usuarioRepository.save(usuario);
        return true;
    }
    
    // 🔹 NUEVO: Obtener usuario por email
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}