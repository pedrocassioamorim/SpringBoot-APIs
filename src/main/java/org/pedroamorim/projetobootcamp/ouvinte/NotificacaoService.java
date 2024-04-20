package org.pedroamorim.projetobootcamp.ouvinte;

import org.pedroamorim.projetobootcamp.notificacao.NivelUrgencia;
import org.pedroamorim.projetobootcamp.notificacao.Notificador;
import org.pedroamorim.projetobootcamp.notificacao.TipoDoNotificador;
import org.pedroamorim.projetobootcamp.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoService {
    @TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
    @Autowired
    private Notificador notificador;

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event){
        notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo!");
    }
}
