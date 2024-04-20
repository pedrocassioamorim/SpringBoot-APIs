package org.pedroamorim.projetobootcamp.notificacao;

import org.pedroamorim.projetobootcamp.modelo.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod")
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmail implements Notificador {

    @Value("${notificador.email.host-servidor}")
    private String host;

    @Value("${notificador.email.porta-servidor}")
    private Integer porta;

    public NotificadorEmail(){
        System.out.println("Noficador de E-mail Real");
    }

    @Override
    public void notificar(Cliente cliente, String mensagem){
        System.out.println("Host: " + host);
        System.out.println("Porta: " + porta);
        System.out.printf("Notificando %s através do e-mail %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }

}
