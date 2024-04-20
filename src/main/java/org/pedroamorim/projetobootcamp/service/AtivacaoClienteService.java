package org.pedroamorim.projetobootcamp.service;

import org.pedroamorim.projetobootcamp.modelo.Cliente;
import org.pedroamorim.projetobootcamp.notificacao.NivelUrgencia;
import org.pedroamorim.projetobootcamp.notificacao.Notificador;
import org.pedroamorim.projetobootcamp.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class AtivacaoClienteService {

    @TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
    @Autowired
    private Notificador notificador;

    @PostConstruct
    public void init(){
        System.out.println("INIT: " + notificador);
    }

    @PreDestroy
    public void destroy(){
        System.out.println("DESTROY");
    }


    public void ativar(Cliente cliente){
        cliente.ativar();
        if (notificador != null) {
            notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
        } else {
            System.out.println("Não existe Notificador, mas o cliente foi ativado");
        }
    }

}
