package com.turismo.guiasweb.controller;

import com.turismo.guiasweb.model.Guia;
import com.turismo.guiasweb.model.GuiaRepository;
import com.turismo.guiasweb.model.Reserva;
import com.turismo.guiasweb.model.ReservaRepository;
import com.turismo.guiasweb.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ReservaController {
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private GuiaRepository guiaRepository;
    
    // ===== FORMULARIO DE RESERVA =====
    @GetMapping("/reservar/{id}")
    public String mostrarReserva(@PathVariable int id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        Guia guia = guiaRepository.findById(id).orElse(null);
        if (guia == null) {
            return "redirect:/buscar";
        }
        
        model.addAttribute("guia", guia);
        model.addAttribute("usuario", usuario);
        return "reservar";
    }
    
    // ===== PROCESAR RESERVA CON VALIDACIONES =====
    @PostMapping("/reservar/confirmar")
    public String confirmarReserva(@RequestParam int guiaId,
                                   @RequestParam String fecha,
                                   @RequestParam String hora,
                                   @RequestParam int horas,
                                   HttpSession session,
                                   Model model) {
        
        // 1. Verificar sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // 2. Buscar guía
        Guia guia = guiaRepository.findById(guiaId).orElse(null);
        if (guia == null) {
            model.addAttribute("error", "❌ El guía no existe");
            return "buscar";
        }
        
        // 3. Validar disponibilidad del guía
        if (guia.getDisponibilidad() != 1) {
            model.addAttribute("error", "❌ Este guía ya no está disponible");
            model.addAttribute("guia", guia);
            return "reservar";
        }
        
        // 4. Parsear fecha y hora
        LocalDate fechaReserva = LocalDate.parse(fecha);
        LocalTime horaReserva = LocalTime.parse(hora);
        
        // 5. ✅ CORREGIDO: Validar fecha pasada
        if (fechaReserva.isBefore(LocalDate.now())) {
            model.addAttribute("error", "❌ No puedes reservar en una fecha pasada");
            model.addAttribute("guia", guia);
            return "reservar";
        }
        
        // 6. ✅ CORREGIDO: Verificar si ya existe una reserva
        List<Reserva> reservasExistentes = reservaRepository.findByGuiaIdAndFechaAndHora(
            guiaId, fechaReserva, horaReserva
        );
        
        if (!reservasExistentes.isEmpty()) {
            model.addAttribute("error", "❌ Este guía ya tiene una reserva para el " + 
                               fechaReserva + " a las " + horaReserva + ". Por favor, elige otro horario.");
            model.addAttribute("guia", guia);
            model.addAttribute("mostrarModal", true);
            return "reservar";
        }
        
        // 7. ✅ CORREGIDO: Validar límite diario (máximo 3 reservas por día)
        int reservasDia = reservaRepository.countByGuiaIdAndFecha(guiaId, fechaReserva);
        if (reservasDia >= 3) {
            model.addAttribute("error", "❌ El guía ya tiene 3 reservas para este día (máximo permitido)");
            model.addAttribute("guia", guia);
            model.addAttribute("mostrarModal", true);
            return "reservar";
        }
        
        // 8. Calcular costo total
        BigDecimal costoTotal = guia.getTarifaHora().multiply(new BigDecimal(horas));
        
        // 9. Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setGuia(guia);
        reserva.setFecha(fechaReserva);
        reserva.setHora(horaReserva);
        reserva.setHorasContratadas(horas);
        reserva.setCostoTotal(costoTotal);
        
        reservaRepository.save(reserva);
        
        // 10. Marcar guía como no disponible
        guia.setDisponibilidad(0);
        guiaRepository.save(guia);
        
        // Mensaje de éxito
        model.addAttribute("success", "✅ ¡Reserva confirmada con éxito!");
        model.addAttribute("reserva", reserva);
        
        return "reservar";
    }
}