package org.pedroamorim.projetobootcamp;

import org.pedroamorim.projetobootcamp.notificacao.NotificadorEmail;
import org.pedroamorim.projetobootcamp.service.AtivacaoClienteService;
import org.springframework.context.annotation.Bean;

//@Configuration
public class AlgaConfig {

    @Bean
    public NotificadorEmail notificadorEmail(){
        NotificadorEmail notificadorEmail = new NotificadorEmail("smtp.algamail.com.br");
        notificadorEmail.setCaixaAlta(true);
        return notificadorEmail;
    }

    @Bean
    public AtivacaoClienteService ativacaoClienteService(){
        return new AtivacaoClienteService(notificadorEmail());
    }
}
