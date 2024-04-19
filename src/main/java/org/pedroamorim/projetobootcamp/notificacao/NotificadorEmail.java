package org.pedroamorim.projetobootcamp.notificacao;

import org.pedroamorim.projetobootcamp.modelo.Cliente;
import org.springframework.stereotype.Component;

@Component
public class NotificadorEmail {

    public NotificadorEmail() {
        System.out.println("Notificador Email");
    }

    public void notificar (Cliente cliente, String mensagem){
        System.out.printf("Notificando %s através do e-mail %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
