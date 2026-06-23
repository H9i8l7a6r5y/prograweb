package com.turismo.guiasweb.controller;

import com.turismo.guiasweb.model.Destino;
import com.turismo.guiasweb.model.Guia;
import com.turismo.guiasweb.model.DestinoRepository;
import com.turismo.guiasweb.model.GuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class GuiaController {
    
    @Autowired
    private DestinoRepository destinoRepository;
    
    @Autowired
    private GuiaRepository guiaRepository;
    
    @GetMapping("/buscar")
    public String mostrarBusqueda(Model model) {
        List<Destino> destinos = destinoRepository.findAll();
        model.addAttribute("destinos", destinos);
        return "buscar";
    }
    
    @PostMapping("/buscar")
    public String procesarBusqueda(@RequestParam int destinoId, Model model) {
        List<Guia> guias = guiaRepository.findGuiasPorDestino(destinoId);
        model.addAttribute("guias", guias);
        
        List<Destino> destinos = destinoRepository.findAll();
        model.addAttribute("destinos", destinos);
        model.addAttribute("destinoSeleccionado", destinoId);
        
        return "buscar";
    }
    @GetMapping("/reservar/{id}")
    public String mostrarReserva(@PathVariable int id, Model model) {
    Guia guia = guiaRepository.findById(id).orElse(null);
    model.addAttribute("guia", guia);
    return "reservar";
}
}