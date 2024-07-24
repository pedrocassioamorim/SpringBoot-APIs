package org.pedroamorim.projetobootcamp.services.exceptions;

public class RequisicaoRuimException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public RequisicaoRuimException(String mensagem){
        super(mensagem);
    }

}
