package org.pedroamorim.projetobootcamp;

import org.pedroamorim.projetobootcamp.modelo.Cliente;
import org.pedroamorim.projetobootcamp.service.AtivacaoClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MeuPrimeiroController {
    private AtivacaoClienteService ativacaoClienteService;

    public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
        this.ativacaoClienteService = ativacaoClienteService;
        System.out.println("Meu Primeiro Controller: " + ativacaoClienteService);
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        Cliente joao = new Cliente("Joao", "joao@gmail.com", "9294849383", false);
        ativacaoClienteService.ativar(joao);
        return "Olá Mundo, by: Pedro Amorim";
    }
}
