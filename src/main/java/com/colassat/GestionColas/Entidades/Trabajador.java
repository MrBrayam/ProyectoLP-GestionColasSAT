package com.colassat.GestionColas.Entidades;

public class Trabajador {
    private String idTrabajador;    // VARCHAR(10) NOT NULL
    private String nomTrabajador;   // VARCHAR(50)
    private boolean estadoMaster;   // BIT NOT NULL
    private boolean estadoTrabajador; // BIT NOT NULL

    public Trabajador() {
    }

    public Trabajador(String idTrabajador, String nomTrabajador, boolean estadoMaster, boolean estadoTrabajador) {
        this.idTrabajador = idTrabajador;
        this.nomTrabajador = nomTrabajador;
        this.estadoMaster = estadoMaster;
        this.estadoTrabajador = estadoTrabajador;
    }

    public String getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getNomTrabajador() {
        return nomTrabajador;
    }

    public void setNomTrabajador(String nomTrabajador) {
        this.nomTrabajador = nomTrabajador;
    }

    public boolean isEstadoMaster() {
        return estadoMaster;
    }

    public void setEstadoMaster(boolean estadoMaster) {
        this.estadoMaster = estadoMaster;
    }

    public boolean isEstadoTrabajador() {
        return estadoTrabajador;
    }

    public void setEstadoTrabajador(boolean estadoTrabajador) {
        this.estadoTrabajador = estadoTrabajador;
    }

    @Override
    public String toString() {
        return "Trabajador: " + nomTrabajador + ", ID: " + idTrabajador + 
               ", Estado Master: " + (estadoMaster ? "Activo" : "Inactivo") + 
               ", Estado Trabajador: " + (estadoTrabajador ? "Activo" : "Inactivo");
    }
}
