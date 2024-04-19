package org.pedroamorim.projetobootcamp.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cliente {
    private String nome;
    private String email;
    private String telefone;
    private boolean ativo = false;


    public void ativar(){
        this.ativo = true;
    }
}
