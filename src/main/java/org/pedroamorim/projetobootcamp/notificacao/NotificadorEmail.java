package org.pedroamorim.projetobootcamp.notificacao;

import org.pedroamorim.projetobootcamp.modelo.Cliente;


public class NotificadorEmail implements Notificador {

    private boolean caixaAlta;
    private String hostServidorSMTP;

    public NotificadorEmail(String hostServidorSMTP) {
        this.hostServidorSMTP = hostServidorSMTP;
        System.out.println("Notificador Email");
    }

    @Override
    public void notificar(Cliente cliente, String mensagem){
        if (this.caixaAlta){
            mensagem = mensagem.toUpperCase();
        }
        System.out.printf("Notificando %s através do e-mail %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }

    public void setCaixaAlta(boolean caixaAlta) {
        this.caixaAlta = caixaAlta;
    }
}
