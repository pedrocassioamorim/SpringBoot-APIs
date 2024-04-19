package org.pedroamorim.projetobootcamp.notificacao;

import org.pedroamorim.projetobootcamp.modelo.Cliente;

public interface Notificador {
    void notificar(Cliente cliente, String mensagem);
}
