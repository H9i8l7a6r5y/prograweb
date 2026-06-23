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
    
    // 🔹 MÉTODO LOGIN (el que usa tu controlador)
    public Usuario login(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmailAndPassword(email, password);
        return usuario.orElse(null);
    }
    
    // 🔹 MÉTODO REGISTRAR
    public boolean registrar(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            return false;
        }
        usuarioRepository.save(usuario);
        return true;
    }
}