package com.turismo.guiasweb;

import com.turismo.guiasweb.config.ConfigManager;
import com.turismo.guiasweb.ui.DatabaseConfigDialog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class GuiaswebApplication {
    
    public static void main(String[] args) {
        // Verificar si estamos en modo producción (variables de entorno)
        String dbUrl = System.getenv("SPRING_DATASOURCE_URL");
        
        if (dbUrl != null && !dbUrl.isEmpty()) {
            // MODO PRODUCCIÓN: No mostrar ventana, iniciar directamente
            System.out.println("🚀 Modo producción detectado - Iniciando sin interfaz");
            SpringApplication.run(GuiaswebApplication.class, args);
            return;
        }
        
        // MODO DESARROLLO: Mostrar ventana de configuración
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            boolean configExists = ConfigManager.configExists();
            
            if (configExists) {
                ConfigManager.loadConfig();
                
                int option = JOptionPane.showConfirmDialog(null,
                    "📋 Configuración actual:\n" +
                    ConfigManager.getConfigInfo() +
                    "\n¿Deseas usar esta configuración?",
                    "Configuración encontrada",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (option == JOptionPane.NO_OPTION) {
                    showConfigDialog();
                } else {
                    startApplication();
                }
            } else {
                showConfigDialog();
            }
        });
    }
    
    private static void showConfigDialog() {
        DatabaseConfigDialog dialog = new DatabaseConfigDialog();
        dialog.setVisible(true);
        
        if (dialog.isConfigSaved()) {
            startApplication();
        } else {
            System.err.println("❌ Configuración cancelada. Saliendo...");
            System.exit(0);
        }
    }
    
    private static void startApplication() {
        System.out.println("🚀 Iniciando aplicación con configuración:");
        System.out.println(ConfigManager.getConfigInfo());
        
        new Thread(() -> {
            try {
                SpringApplication.run(GuiaswebApplication.class, new String[]{});
            } catch (Exception e) {
                System.err.println("❌ Error al iniciar Spring Boot: " + e.getMessage());
                JOptionPane.showMessageDialog(null,
                    "❌ Error al iniciar la aplicación:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }
}