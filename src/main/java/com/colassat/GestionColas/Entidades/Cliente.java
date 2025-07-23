package com.colassat.GestionColas.Entidades;

public class Cliente {
    private String codCliente;
    private String nomCliente;

    public Cliente() {
    }

    public Cliente(String codCliente, String nomCliente) {
        this.codCliente = codCliente;
        this.nomCliente = nomCliente;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    @Override
    public String toString() {
        return "Cliente: " + nomCliente + ", Codigo: " + codCliente;
    }
}
