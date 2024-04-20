package org.pedroamorim.projetobootcamp.notificacao;

import org.pedroamorim.projetobootcamp.modelo.Cliente;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class NotificadorSMS implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem){
        System.out.printf("Notificando %s através do SMS de telefone %s: %s\n",
                cliente.getNome(), cliente.getTelefone(), mensagem);
    }

}
