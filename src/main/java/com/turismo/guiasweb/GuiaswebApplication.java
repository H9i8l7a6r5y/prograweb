package com.turismo.guiasweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GuiaswebApplication {

    public static void main(String[] args) {
        // En Railway se usa SPRING_DATASOURCE_URL (variable de entorno)
        // En local, si no hay variable, usará la ventana de configuración
        SpringApplication.run(GuiaswebApplication.class, args);
    }
}