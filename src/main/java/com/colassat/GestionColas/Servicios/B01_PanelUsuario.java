package com.colassat.GestionColas.Servicios;

import javax.swing.*;
import java.awt.*;

public class B01_PanelUsuario extends JFrame {
    private String usuarioActual;
    private JLabel lblBienvenida;
    private JButton btnSolicitarTurno;
    private JButton btnVerTurnos;
    private JButton btnCancelarTurno;
    private JButton btnSalir;
    private JTextArea txtAreaInfo;
    
    public B01_PanelUsuario(String usuario) {
        this.usuarioActual = usuario;
        initComponents();
        setupLayout();
    }
    
    private void initComponents() {
        setTitle("Panel de Usuario - " + usuarioActual);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setResizable(true); 
        
        lblBienvenida = new JLabel("Bienvenido, " + usuarioActual);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setForeground(new Color(0, 123, 255));
        
        btnSolicitarTurno = new JButton("Solicitar Turno");
        btnVerTurnos = new JButton("Ver Mis Turnos");
        btnCancelarTurno = new JButton("Cancelar Turno");
        btnSalir = new JButton("Salir");
        
        txtAreaInfo = new JTextArea(15, 40);
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaInfo.setBorder(BorderFactory.createTitledBorder("Información de Turnos"));
        
        configurarBoton(btnSolicitarTurno, new Color(40, 167, 69));
        configurarBoton(btnVerTurnos, new Color(0, 123, 255));
        configurarBoton(btnCancelarTurno, new Color(220, 53, 69));
        configurarBoton(btnSalir, new Color(108, 117, 125));
    }
    
    private void configurarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setPreferredSize(new Dimension(150, 40));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSuperior.setBackground(Color.BLACK);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelSuperior.add(lblBienvenida);
        
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBotones.add(btnSolicitarTurno);
        panelBotones.add(btnVerTurnos);
        panelBotones.add(btnCancelarTurno);
        panelBotones.add(btnSalir);
        
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(panelBotones, BorderLayout.NORTH);
        panelCentral.add(new JScrollPane(txtAreaInfo), BorderLayout.CENTER);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
    }
    
}