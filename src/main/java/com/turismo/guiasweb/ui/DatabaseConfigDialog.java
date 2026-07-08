package com.turismo.guiasweb.ui;

import com.turismo.guiasweb.config.ConfigManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DatabaseConfigDialog extends JDialog {
    private JTextField txtHost, txtPort, txtDatabase, txtUser;
    private JPasswordField txtPassword;
    private JButton btnSave, btnTest, btnCancel;
    private boolean configSaved = false;
    private JLabel lblStatus;
    
    public DatabaseConfigDialog() {
        this(null);
    }
    
    public DatabaseConfigDialog(JFrame parent) {
        super(parent, "⚙️ Configuración de Base de Datos", true);
        setSize(500, 420);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        initComponents();
        loadExistingConfig();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("🔐 Configuración de Conexión a Base de Datos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(33, 97, 145));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JSeparator separator = new JSeparator();
        panel.add(separator, gbc);
        
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Host
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblHost = new JLabel("🌐 Host/IP:");
        lblHost.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lblHost, gbc);
        
        txtHost = new JTextField(20);
        txtHost.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHost.setToolTipText("Ejemplo: localhost, 192.168.1.100, o mysql.railway.internal");
        gbc.gridx = 1;
        panel.add(txtHost, gbc);
        
        // Puerto
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lblPort = new JLabel("🔌 Puerto:");
        lblPort.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lblPort, gbc);
        
        txtPort = new JTextField(20);
        txtPort.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPort.setToolTipText("Puerto predeterminado: 3306");
        gbc.gridx = 1;
        panel.add(txtPort, gbc);
        
        // Base de datos
        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel lblDatabase = new JLabel("📁 Base de datos:");
        lblDatabase.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lblDatabase, gbc);
        
        txtDatabase = new JTextField(20);
        txtDatabase.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDatabase.setToolTipText("Nombre de la base de datos en MySQL");
        gbc.gridx = 1;
        panel.add(txtDatabase, gbc);
        
        // Usuario
        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel lblUser = new JLabel("👤 Usuario:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lblUser, gbc);
        
        txtUser = new JTextField(20);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUser.setToolTipText("Usuario de MySQL (ej: root)");
        gbc.gridx = 1;
        panel.add(txtUser, gbc);
        
        // Contraseña
        gbc.gridy = 6;
        gbc.gridx = 0;
        JLabel lblPassword = new JLabel("🔑 Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lblPassword, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setToolTipText("Contraseña de MySQL (dejar vacío si no tiene)");
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);
        
        // Estado
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblStatus = new JLabel("ℹ️ Ingresa los datos de tu servidor MySQL");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblStatus.setForeground(Color.GRAY);
        panel.add(lblStatus, gbc);
        
        add(panel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 10));
        
        btnTest = new JButton("🔗 Probar Conexión");
        btnTest.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnTest.setBackground(new Color(52, 152, 219));
        btnTest.setForeground(Color.WHITE);
        btnTest.setFocusPainted(false);
        btnTest.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnSave = new JButton("💾 Guardar y Conectar");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCancel = new JButton("✖ Cancelar");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancel.setBackground(new Color(231, 76, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnTest.addActionListener(this::testConnection);
        btnSave.addActionListener(this::saveAndConnect);
        btnCancel.addActionListener(e -> dispose());
        
        buttonPanel.add(btnTest);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        getRootPane().setDefaultButton(btnSave);
    }
    
    private void loadExistingConfig() {
        if (ConfigManager.configExists()) {
            ConfigManager.loadConfig();
            txtHost.setText("localhost");
            txtPort.setText("3306");
            txtDatabase.setText("turismo_db");
            txtUser.setText(ConfigManager.getUsername());
        } else {
            txtHost.setText("localhost");
            txtPort.setText("3306");
            txtDatabase.setText("turismo_db");
            txtUser.setText("root");
        }
    }
    
    private void testConnection(ActionEvent e) {
        String url = buildConnectionUrl();
        String user = txtUser.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        lblStatus.setText("⏳ Probando conexión...");
        lblStatus.setForeground(Color.ORANGE);
        btnTest.setEnabled(false);
        
        new Thread(() -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                java.sql.Connection conn = java.sql.DriverManager.getConnection(url, user, password);
                conn.close();
                
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("✅ ¡Conexión exitosa!");
                    lblStatus.setForeground(new Color(46, 204, 113));
                    JOptionPane.showMessageDialog(DatabaseConfigDialog.this, 
                        "✅ ¡Conexión exitosa!\n\nURL: " + url,
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    btnTest.setEnabled(true);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("❌ Error: " + ex.getMessage());
                    lblStatus.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(DatabaseConfigDialog.this, 
                        "❌ Error de conexión:\n\n" + ex.getMessage(),
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    btnTest.setEnabled(true);
                });
            }
        }).start();
    }
    
    private void saveAndConnect(ActionEvent e) {
        String host = txtHost.getText().trim();
        String port = txtPort.getText().trim();
        String dbName = txtDatabase.getText().trim();
        String user = txtUser.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (host.isEmpty() || port.isEmpty() || dbName.isEmpty() || user.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Todos los campos son obligatorios", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ConfigManager.saveConfig(host, port, dbName, user, password);
        configSaved = true;
        
        JOptionPane.showMessageDialog(this, 
            "✅ Configuración guardada exitosamente",
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
    
    private String buildConnectionUrl() {
        String host = txtHost.getText().trim();
        String port = txtPort.getText().trim();
        String dbName = txtDatabase.getText().trim();
        return String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", 
                           host, port, dbName);
    }
    
    public boolean isConfigSaved() {
        return configSaved;
    }
}