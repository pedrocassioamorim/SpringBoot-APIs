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


    @Autowired
    private CidadeRepository cidadeRepository;


    @Autowired
    private EstadoRepository estadoRepository;


    public List<Cidade> listar(){
        return cidadeRepository.findAll();}

    public Cidade buscar (Long Id){

        Optional<Cidade> cidade = cidadeRepository.findById(Id);

        if (!cidade.isPresent()){
            throw new EntidadeNaoEncontradaException(String.format("Cidade de ID %d nao encontrada", Id));
        }
        return cidade.get();
    }

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estado = estadoRepository.findById(estadoId);
        if (!estado.isPresent()){
            throw new RequisicaoRuimException(String.format("Não existe um Estado com o ID %d", estadoId));
        }
        cidade.setEstado(estado.get());
        return cidadeRepository.save(cidade);
    }

    public Cidade atualizar (Long Id, Cidade cidade){
        Optional<Cidade> cidadeAtualizar = cidadeRepository.findById(Id);
        if (cidadeAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format("Cidade de ID %d nao encontrada", Id));
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
            throw new EntidadeNaoEncontradaException(String.format("Cidade de ID %d nao encontrada", Id));
        } catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format("Cidade de ID %d nao pode ser removida pois esta sendo usada", Id));
        }
    }

}
