package com.colassat.GestionColas.Servicios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class B03_PanelTrabajador extends JFrame {
    private String usuarioTrabajador;
    private JLabel lblBienvenida;
    private JButton btnGestionTickets;
    private JButton btnVolver;
    
    public B03_PanelTrabajador(String usuario) {
        this.usuarioTrabajador = usuario;
        initComponents();
        setupLayout();
        setupEvents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Panel de Trabajador - " + usuarioTrabajador);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setResizable(true);
        
        lblBienvenida = new JLabel("Bienvenido Trabajador: " + usuarioTrabajador);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setForeground(new Color(40, 167, 69));
        
        btnGestionTickets = new JButton("GESTIÓN DE TICKETS");
        btnVolver = new JButton("VOLVER");

        configurarBoton(btnGestionTickets, new Color(0, 123, 255));
        configurarBoton(btnVolver, new Color(108, 117, 125));
    }
    
    private void configurarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setPreferredSize(new Dimension(200, 50));
        boton.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(248, 249, 250));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelSuperior.add(lblBienvenida);
        
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(btnGestionTickets, gbc);
        
        gbc.gridy = 1;
        panelCentral.add(btnVolver, gbc);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }
    
    private void setupEvents() {
        btnGestionTickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionTickets();
            }
        });
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volver();
            }
        });
    }
    
    private void abrirGestionTickets() {
        try {
            C02_VentanaCRUDTickets ventanaTickets = new C02_VentanaCRUDTickets("TRABAJADOR", usuarioTrabajador);
            ventanaTickets.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al abrir gestión de tickets: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void volver() {
        try {
            A01_VentanaInicio ventanaInicio = new A01_VentanaInicio();
            ventanaInicio.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al volver: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
