package com.colassat.GestionColas.Entidades;

import java.sql.Date;
import java.sql.Time;

public class Ticket {
    private Date fechaTicket;        
    private String numTicket;         
    private Time horaEmisionTicket;   
    private String codCliente;        
    private String codServ;            
    private String idTrabajador;       
    private boolean estadoTicket;       
    private Time horaAtencionTicket;    

    public Ticket() {
    }

    public Ticket(Date fechaTicket, String numTicket, Time horaEmisionTicket, 
                  String codCliente, String codServ, String idTrabajador, 
                  boolean estadoTicket, Time horaAtencionTicket) {
        this.fechaTicket = fechaTicket;
        this.numTicket = numTicket;
        this.horaEmisionTicket = horaEmisionTicket;
        this.codCliente = codCliente;
        this.codServ = codServ;
        this.idTrabajador = idTrabajador;
        this.estadoTicket = estadoTicket;
        this.horaAtencionTicket = horaAtencionTicket;
    }

    public Date getFechaTicket() {
        return fechaTicket;
    }

    public void setFechaTicket(Date fechaTicket) {
        this.fechaTicket = fechaTicket;
    }

    public String getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(String numTicket) {
        this.numTicket = numTicket;
    }

    public Time getHoraEmisionTicket() {
        return horaEmisionTicket;
    }

    public void setHoraEmisionTicket(Time horaEmisionTicket) {
        this.horaEmisionTicket = horaEmisionTicket;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getCodServ() {
        return codServ;
    }

    public void setCodServ(String codServ) {
        this.codServ = codServ;
    }

    public String getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public boolean isEstadoTicket() {
        return estadoTicket;
    }

    public void setEstadoTicket(boolean estadoTicket) {
        this.estadoTicket = estadoTicket;
    }

    public Time getHoraAtencionTicket() {
        return horaAtencionTicket;
    }

    public void setHoraAtencionTicket(Time horaAtencionTicket) {
        this.horaAtencionTicket = horaAtencionTicket;
    }

    @Override
    public String toString() {
        return "Ticket: " + numTicket + ", Fecha: " + fechaTicket + 
               ", Cliente: " + codCliente + ", Servicio: " + codServ + 
               ", Trabajador: " + idTrabajador + 
               ", Estado: " + (estadoTicket ? "Activo" : "Inactivo");
    }
}
