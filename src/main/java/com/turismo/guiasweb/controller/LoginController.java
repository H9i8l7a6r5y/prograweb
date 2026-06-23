package com.turismo.guiasweb.controller;

import com.turismo.guiasweb.service.Usuarioservices;
import com.turismo.guiasweb.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.turismo.guiasweb.model.Reserva;
import com.turismo.guiasweb.model.ReservaRepository;
import java.util.List;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    @Autowired
    private Usuarioservices usuarioservices;
    
    @Autowired
     private ReservaRepository reservaRepository;
    
    // Mostrar login
    @GetMapping("/")
    public String mostrarLogin() {
        return "login";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    // Mostrar registro
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }
    
    // PROCESAR REGISTRO (esto es lo que faltaba)
    @PostMapping("/registro")
    public String procesarRegistro(@RequestParam String nombre,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   Model model) {
        Usuario nuevoUsuario = new Usuario(nombre, email, password);
        
        if (usuarioservices.registrar(nuevoUsuario)) {
            model.addAttribute("success", "✅ Registro exitoso. Ahora puedes iniciar sesión.");
            return "login";
        } else {
            model.addAttribute("error", "❌ El email ya está registrado");
            return "registro";
        }
    }
    
    // Procesar login
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        Usuario usuario = usuarioservices.login(email, password);
        
        if (usuario != null) {
            session.setAttribute("usuario", usuario);
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "Email o contraseña incorrectos");
            return "login";
        }
    }
    
    // Mostrar menú
    @GetMapping("/menu")
    public String mostrarMenu(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "menu";
    }
    
    // Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    @GetMapping("/misreservas")
public String mostrarMisReservas(HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        return "redirect:/login";
    }
    
    
     List<Reserva> reservas = reservaRepository.findByUsuarioId(usuario.getId());
    model.addAttribute("reservas", reservas);
    return "misreservas";
}
}