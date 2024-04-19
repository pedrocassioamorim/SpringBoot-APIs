package org.pedroamorim.projetobootcamp;

import org.pedroamorim.projetobootcamp.notificacao.Notificador;
import org.pedroamorim.projetobootcamp.service.AtivacaoClienteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public AtivacaoClienteService ativacaoClienteService(Notificador notificador){
        return new AtivacaoClienteService(notificador);
    }
}
