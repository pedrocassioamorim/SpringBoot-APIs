package org.pedroamorim.projetobootcamp.services;

import org.pedroamorim.projetobootcamp.domain.model.Cidade;
import org.pedroamorim.projetobootcamp.domain.model.Estado;
import org.pedroamorim.projetobootcamp.repositories.CidadeRepository;
import org.pedroamorim.projetobootcamp.repositories.EstadoRepository;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.services.exceptions.RequisicaoRuimException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroCidadeService {


    public static final String CIDADE_DE_ID_D_NAO_ENCONTRADA = "Cidade de ID %d nao encontrada";
    public static final String NAO_EXISTE_UM_ESTADO_COM_O_ID_D = "Não existe um Estado com o ID %d";
    public static final String CIDADE_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_SENDO_USADA = "Cidade de ID %d nao pode ser removida pois esta sendo usada";


    @Autowired
    private CidadeRepository cidadeRepository;


    @Autowired
    private EstadoRepository estadoRepository;


    public List<Cidade> listar(){
        return cidadeRepository.findAll();}

    public Cidade buscar (Long Id){

        Optional<Cidade> cidade = cidadeRepository.findById(Id);

        if (!cidade.isPresent()){
            throw new EntidadeNaoEncontradaException(String.format(CIDADE_DE_ID_D_NAO_ENCONTRADA, Id));
        }
        return cidade.get();
    }

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estado = estadoRepository.findById(estadoId);
        if (!estado.isPresent()){
            throw new RequisicaoRuimException(String.format(NAO_EXISTE_UM_ESTADO_COM_O_ID_D, estadoId));
        }
        cidade.setEstado(estado.get());
        return cidadeRepository.save(cidade);
    }

    public Cidade atualizar (Long Id, Cidade cidade){
        Optional<Cidade> cidadeAtualizar = cidadeRepository.findById(Id);
        if (cidadeAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(CIDADE_DE_ID_D_NAO_ENCONTRADA, Id));
        } else {
            BeanUtils.copyProperties(cidade, cidadeAtualizar.get(), "id");
            return salvar(cidade);
        }
    }

    public void excluir (Long Id){
        try{
            Optional<Cidade> cidadeAtualizar = cidadeRepository.findById(Id);
            if (cidadeAtualizar.isPresent()){
                cidadeRepository.delete(cidadeAtualizar.get());
            }
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format(CIDADE_DE_ID_D_NAO_ENCONTRADA, Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format(CIDADE_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_SENDO_USADA, Id));
        }
    }

}
