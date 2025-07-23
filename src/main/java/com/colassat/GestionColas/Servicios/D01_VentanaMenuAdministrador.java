package com.colassat.GestionColas.Servicios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class D01_VentanaMenuAdministrador extends JFrame {
    private JButton btnCRUDTrabajadores;
    private JButton btnCRUDTickets;
    private JButton btnCRUDServicios;
    private JButton btnCRUDClientes;
    private JButton btnGenerarReporte;
    private JButton btnVolver;
    
    public D01_VentanaMenuAdministrador() {
        initComponents();
        setupLayout();
        setupEvents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Panel Administrador - Sistema de Gestion de Colas SAT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setSize(700, 550);
        
        btnCRUDTrabajadores = new JButton("CRUD TRABAJADORES");
        btnCRUDTickets = new JButton("CRUD TICKETS");
        btnCRUDServicios = new JButton("CRUD SERVICIOS");
        btnCRUDClientes = new JButton("CRUD CLIENTES");
        btnGenerarReporte = new JButton("GENERAR REPORTE");
        btnVolver = new JButton("VOLVER");
        
        Font btnFont = new Font("Arial", Font.BOLD, 16);
        Dimension btnSize = new Dimension(250, 50);
        
        Color btnColor = new Color(0, 123, 255);
        Color volverColor = new Color(108, 117, 125);
        Color reporteColor = new Color(40, 167, 69);
        
        btnCRUDTrabajadores.setFont(btnFont);
        btnCRUDTrabajadores.setBackground(btnColor);
        btnCRUDTrabajadores.setForeground(Color.BLACK);
        btnCRUDTrabajadores.setPreferredSize(btnSize);
        btnCRUDTrabajadores.setFocusPainted(false);
        
        btnCRUDTickets.setFont(btnFont);
        btnCRUDTickets.setBackground(btnColor);
        btnCRUDTickets.setForeground(Color.BLACK);
        btnCRUDTickets.setPreferredSize(btnSize);
        btnCRUDTickets.setFocusPainted(false);
        
        btnCRUDServicios.setFont(btnFont);
        btnCRUDServicios.setBackground(btnColor);
        btnCRUDServicios.setForeground(Color.BLACK);
        btnCRUDServicios.setPreferredSize(btnSize);
        btnCRUDServicios.setFocusPainted(false);
        
        btnCRUDClientes.setFont(btnFont);
        btnCRUDClientes.setBackground(btnColor);
        btnCRUDClientes.setForeground(Color.BLACK);
        btnCRUDClientes.setPreferredSize(btnSize);
        btnCRUDClientes.setFocusPainted(false);
        
        btnGenerarReporte.setFont(btnFont);
        btnGenerarReporte.setBackground(reporteColor);
        btnGenerarReporte.setForeground(Color.WHITE);
        btnGenerarReporte.setPreferredSize(btnSize);
        btnGenerarReporte.setFocusPainted(false);
        
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.setBackground(volverColor);
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setPreferredSize(new Dimension(100, 35));
        btnVolver.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(248, 249, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        
        JLabel lblTitulo = new JLabel("PANEL ADMINISTRADOR");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(52, 58, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelPrincipal.add(lblTitulo, gbc);
        
        JLabel lblSubtitulo = new JLabel("Seleccione una opcion:");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        gbc.gridy = 1;
        panelPrincipal.add(lblSubtitulo, gbc);
        
        gbc.gridy = 2;
        panelPrincipal.add(btnCRUDTrabajadores, gbc);
        
        gbc.gridy = 3;
        panelPrincipal.add(btnCRUDTickets, gbc);
        
        gbc.gridy = 4;
        panelPrincipal.add(btnCRUDServicios, gbc);
        
        gbc.gridy = 5;
        panelPrincipal.add(btnCRUDClientes, gbc);
        
        gbc.gridy = 6;
        panelPrincipal.add(btnGenerarReporte, gbc);
        
        gbc.gridy = 7;
        gbc.insets = new Insets(30, 10, 10, 10);
        panelPrincipal.add(btnVolver, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private void setupEvents() {
        btnCRUDTrabajadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new D03_VentanaCRUDTrabajadores().setVisible(true);
            }
        });
        
        btnCRUDTickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new C02_VentanaCRUDTickets().setVisible(true);
            }
        });
        
        btnCRUDServicios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new D05_VentanaCRUDServicios().setVisible(true);
            }
        });
        
        btnCRUDClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new D04_VentanaCRUDClientes("admin").setVisible(true);
            }
        });
        
        btnGenerarReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new D06_VentanaGenerarReporte().setVisible(true);
            }
        });
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new A01_VentanaInicio().setVisible(true);
            }
        });
    }
}