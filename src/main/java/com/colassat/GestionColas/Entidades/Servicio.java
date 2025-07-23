package com.colassat.GestionColas.Entidades;

public class Servicio {
    private String codServ;      
    private String nomServ;     
    private boolean estadoServicio;

    public Servicio() {
    }

    public Servicio(String codServ, String nomServ, boolean estadoServicio) {
        this.codServ = codServ;
        this.nomServ = nomServ;
        this.estadoServicio = estadoServicio;
    }

    public String getCodServ() {
        return codServ;
    }

    public void setCodServ(String codServ) {
        this.codServ = codServ;
    }

    public String getNomServ() {
        return nomServ;
    }

    public void setNomServ(String nomServ) {
        this.nomServ = nomServ;
    }

    public boolean isEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(boolean estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    @Override
    public String toString() {
        return "Servicio: " + nomServ + ", Código: " + codServ + 
               ", Estado: " + (estadoServicio ? "Activo" : "Inactivo");
    }
}
