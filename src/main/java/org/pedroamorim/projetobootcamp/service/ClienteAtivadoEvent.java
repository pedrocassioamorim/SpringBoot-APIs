package org.pedroamorim.projetobootcamp.service;

import org.pedroamorim.projetobootcamp.modelo.Cliente;

public class ClienteAtivadoEvent {
    private Cliente cliente;

    public ClienteAtivadoEvent(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
