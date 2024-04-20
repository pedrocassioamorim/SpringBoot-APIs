package org.pedroamorim.projetobootcamp.service;

import org.pedroamorim.projetobootcamp.modelo.Cliente;
import org.pedroamorim.projetobootcamp.notificacao.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AtivacaoClienteService {

    @Autowired(required = false)
    private List<Notificador> notificadores;



    public void ativar(Cliente cliente){
        cliente.ativar();
        for(Notificador notificador : notificadores) {
            if (notificador != null) {
                notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
            } else {
                System.out.println("Não existe Notificador, mas o cliente foi ativado");
            }
        }
    }

}
