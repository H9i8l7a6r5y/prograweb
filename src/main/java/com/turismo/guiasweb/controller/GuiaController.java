package com.turismo.guiasweb.controller;

import com.turismo.guiasweb.model.Destino;
import com.turismo.guiasweb.model.Guia;
import com.turismo.guiasweb.model.Usuario;
import com.turismo.guiasweb.model.DestinoRepository;
import com.turismo.guiasweb.model.GuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GuiaController {
    
    @Autowired
    private DestinoRepository destinoRepository;
    
    @Autowired
    private GuiaRepository guiaRepository;
    
    // ===== MOSTRAR BÚSQUEDA =====
    @GetMapping("/buscar")
    public String mostrarBusqueda(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        
        List<Destino> destinos = destinoRepository.findAll();
        model.addAttribute("destinos", destinos);
        model.addAttribute("guias", null);
        
        return "buscar";
    }
    
    // ===== PROCESAR BÚSQUEDA =====
    @PostMapping("/buscar")
    public String procesarBusqueda(@RequestParam int destinoId, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        
        List<Guia> guias = guiaRepository.findGuiasPorDestino(destinoId);
        model.addAttribute("guias", guias);
        
        List<Destino> destinos = destinoRepository.findAll();
        model.addAttribute("destinos", destinos);
        model.addAttribute("destinoSeleccionado", destinoId);
        
        return "buscar";
    }
    
    // ❌ ELIMINAR ESTE MÉTODO: está duplicado en ReservaController
    // @GetMapping("/reservar/{id}")
    // public String mostrarReserva(@PathVariable int id, Model model) {
    //     Guia guia = guiaRepository.findById(id).orElse(null);
    //     model.addAttribute("guia", guia);
    //     return "reservar";
    // }
}