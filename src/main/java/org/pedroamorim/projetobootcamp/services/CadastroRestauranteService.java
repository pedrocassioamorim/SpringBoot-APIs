package org.pedroamorim.projetobootcamp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.pedroamorim.projetobootcamp.domain.dtos.RestauranteDto;
import org.pedroamorim.projetobootcamp.domain.model.Cozinha;
import org.pedroamorim.projetobootcamp.domain.model.Restaurante;
import org.pedroamorim.projetobootcamp.repositories.CozinhaRepository;
import org.pedroamorim.projetobootcamp.repositories.RestauranteRepository;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeEmUsoException;
import org.pedroamorim.projetobootcamp.services.exceptions.EntidadeNaoEncontradaException;
import org.pedroamorim.projetobootcamp.services.exceptions.RequisicaoRuimException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CadastroRestauranteService {

    public static final String NAO_FOI_ENCONTRADO_UM_RESTAURANTE_COM_O_ID_D = "Nao foi encontrado um Restaurante com o ID %d";
    public static final String NAO_EXISTE_UMA_COZINHA_PARA_O_ID_D = "Não existe uma cozinha para o ID %d";
    public static final String NAO_PODE_EXCLUIR_RESTAURANTE_DE_CODIGO_D_POIS_ESTA_EM_USO = "Não pode excluir Restaurante de codigo %d pois esta em uso";
    public static final ModelMapper modelMapper = new ModelMapper();


    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CozinhaRepository cozinhaRepository;


    public List<RestauranteDto> listar(){
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        return restaurantes.stream().map(restaurante -> modelMapper.map(restaurante, RestauranteDto.class)).collect(Collectors.toList());
    }

    public RestauranteDto buscar(Long Id){
        Optional<Restaurante> restaurante = restauranteRepository.findById(Id);
        if (restaurante.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(NAO_FOI_ENCONTRADO_UM_RESTAURANTE_COM_O_ID_D, Id));
        }
        return modelMapper.map(restaurante.get(), RestauranteDto.class);
    }

    public List<RestauranteDto> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
        return restaurantes.stream().map(restaurante -> modelMapper.map(restaurante, RestauranteDto.class)).collect(Collectors.toList());
    }

    public List<RestauranteDto> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId) {
        List<Restaurante> restaurantes = restauranteRepository.consultarPorNome(nome, cozinhaId);
        return restaurantes.stream().map((element) -> modelMapper.map(element, RestauranteDto.class)).collect(Collectors.toList());
    }

    public Optional<RestauranteDto> findFirstRestauranteByNomeContaing(String nome) {
        Optional<Restaurante> restaurante = restauranteRepository.findFirstRestauranteByNomeContaining(nome);
        return restaurante.map((element) -> modelMapper.map(element, RestauranteDto.class));
    }

    public List<RestauranteDto> findTop2ByNomeContaing(String nome) {
        List<Restaurante> restaurantes = restauranteRepository.findTop2ByNomeContaining(nome);
        return restaurantes.stream().map((element) -> modelMapper.map(element, RestauranteDto.class)).collect(Collectors.toList());
    }

    public Integer countRestaurantesByCozinhaId(Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    public List<RestauranteDto> consultarPorNomeETaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = restauranteRepository.consultaNomeEFrete(nome, taxaInicial, taxaFinal);
        return restaurantes.stream().map((element) -> modelMapper.map(element, RestauranteDto.class)).collect(Collectors.toList());
    }


    public RestauranteDto salvar(RestauranteDto restauranteDto){
        Long cozinhaId = restauranteDto.getCozinha().getId();
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
        if (cozinha.isEmpty()){
            throw new RequisicaoRuimException(String.format(NAO_EXISTE_UMA_COZINHA_PARA_O_ID_D, cozinhaId));
        }
        restauranteRepository.save(modelMapper.map(restauranteDto, Restaurante.class));
        return restauranteDto;
    }

    public RestauranteDto atualizar(Long Id, RestauranteDto restauranteDto){
        Optional<Restaurante> restauranteAtualizar = restauranteRepository.findById(Id);
        if (restauranteAtualizar.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(NAO_FOI_ENCONTRADO_UM_RESTAURANTE_COM_O_ID_D, Id));
        }
        BeanUtils.copyProperties(restauranteDto, restauranteAtualizar.get(), "id");
        restauranteRepository.save(restauranteAtualizar.get());
        return modelMapper.map(restauranteAtualizar.get(), RestauranteDto.class);
    }

    public RestauranteDto atualizarParcial(Long Id, Map<String, Object> campos){
        Optional<Restaurante> restaurante = restauranteRepository.findById(Id);
        if (restaurante.isEmpty()){
            throw new EntidadeNaoEncontradaException(String.format(NAO_FOI_ENCONTRADO_UM_RESTAURANTE_COM_O_ID_D, Id));
        }

        Restaurante restauranteAtualizar = merge(campos, restaurante.get());
        RestauranteDto restauranteDto = atualizar(Id, modelMapper.map(restauranteAtualizar, RestauranteDto.class));

        return restauranteDto;
    }

    public void excluir (Long Id){
        try{
            Optional<Restaurante> restaurante = restauranteRepository.findById(Id);
            restauranteRepository.delete(restaurante.get());
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format(NAO_FOI_ENCONTRADO_UM_RESTAURANTE_COM_O_ID_D, Id));
        }catch (DataIntegrityViolationException f){
            throw new EntidadeEmUsoException(String.format(NAO_PODE_EXCLUIR_RESTAURANTE_DE_CODIGO_D_POIS_ESTA_EM_USO, Id));
        }
    }

    private Restaurante merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        // UMA SOLUÇÃO MAIS GENÉRICA:
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        System.out.println(restauranteOrigem);

        dadosOrigem.forEach((campo, valor) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, campo);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            System.out.println(campo + " = " + valor + " = " + novoValor);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);



            // UMA SOLUÇÃO MENOS GENÉRICA:
            // Obter o tipo do campo no DTO:
            //Class<?> fieldType = field.getType();

            // ===================== Mapeando formatos especiais (BigDecimal):
//            if(fieldType == BigDecimal.class){
//                ReflectionUtils.setField(field, restauranteDestinoDto, new BigDecimal(String.valueOf(valor)));
//            } else if (fieldType.isAssignableFrom(valor.getClass())) {
//                ReflectionUtils.setField(field, restauranteDestinoDto, valor);
//            } else {
//                throw new IllegalArgumentException("Valor incorreto para o campo:" + campo.toString());
//            }
//            System.out.println(campo + " = " + valor);
        });

        return restauranteDestino;
    }



}
