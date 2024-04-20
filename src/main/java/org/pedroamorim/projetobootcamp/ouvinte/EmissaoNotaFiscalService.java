package org.pedroamorim.projetobootcamp.ouvinte;

import org.pedroamorim.projetobootcamp.service.ClienteAtivadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmissaoNotaFiscalService {

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event){
        System.out.println("Emissão de Nota Fiscal para o cliente: " + event.getCliente());
    }
}
