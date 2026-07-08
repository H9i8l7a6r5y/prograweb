package com.turismo.guiasweb.controller;

import com.turismo.guiasweb.model.Comentario;
import com.turismo.guiasweb.model.ComentarioRepository;
import com.turismo.guiasweb.model.Usuario;
import com.turismo.guiasweb.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Agregar comentario
    @PostMapping("/agregar")
    public String agregarComentario(@RequestParam Integer idGuia,
                                    @RequestParam String comentario,
                                    HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        Comentario nuevo = new Comentario();
        nuevo.setIdGuia(idGuia);
        nuevo.setIdUsuario(usuario.getId());
        nuevo.setComentario(comentario);
        nuevo.setFecha(LocalDateTime.now());
        
        comentarioRepository.save(nuevo);
        return "redirect:/buscar";
    }
    
    // Eliminar comentario (solo admin)
    @GetMapping("/eliminar/{id}")
    public String eliminarComentario(@PathVariable Integer id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.getRol())) {
            return "redirect:/login";
        }
        
        comentarioRepository.deleteById(id);
        return "redirect:/buscar";
    }
}