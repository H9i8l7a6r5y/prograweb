package com.turismo.guiasweb.controller;

import com.turismo.guiasweb.model.Guia;
import com.turismo.guiasweb.model.GuiaRepository;
import com.turismo.guiasweb.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private GuiaRepository guiaRepository;
    
    private boolean esAdmin(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return usuario != null && "ADMIN".equals(usuario.getRol());
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", session.getAttribute("usuario"));
        model.addAttribute("totalGuias", guiaRepository.count());
        return "admin/dashboard";
    }
    
    @GetMapping("/guias")
    public String listarGuias(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        List<Guia> guias = guiaRepository.findAll();
        model.addAttribute("guias", guias);
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "admin/guias";
    }
    
    @GetMapping("/guias/nuevo")
    public String nuevoGuiaForm(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("guia", new Guia());
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "admin/guia-form";
    }
    
    @PostMapping("/guias/guardar")
    public String guardarGuia(@ModelAttribute Guia guia, HttpSession session) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        guiaRepository.save(guia);
        return "redirect:/admin/guias";
    }
    
    @GetMapping("/guias/editar/{id}")
    public String editarGuiaForm(@PathVariable("id") Integer id, HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        Guia guia = guiaRepository.findById(id).orElse(null);
        model.addAttribute("guia", guia);
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "admin/guia-form";
    }
    
    @GetMapping("/guias/eliminar/{id}")
    public String eliminarGuia(@PathVariable("id") Integer id, HttpSession session) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        guiaRepository.deleteById(id);
        return "redirect:/admin/guias";
    }
}