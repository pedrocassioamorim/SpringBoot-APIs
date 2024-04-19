package org.pedroamorim.projetobootcamp.service;

import org.pedroamorim.projetobootcamp.modelo.Cliente;
import org.pedroamorim.projetobootcamp.notificacao.NotificadorEmail;

public class AtivacaoClienteService {
    private NotificadorEmail notificadorEmail;

    public void ativar(Cliente cliente){
        cliente.ativar();

        notificadorEmail.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }
}
