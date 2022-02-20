package br.com.financecontrol.budget.domain.service.impl;

import br.com.financecontrol.budget.domain.dto.input.ReceitaInputDTO;
import br.com.financecontrol.budget.domain.dto.output.ReceitaOutputDTO;
import br.com.financecontrol.budget.domain.exception.ResourceNotFoundException;
import br.com.financecontrol.budget.domain.exception.SameResourceException;
import br.com.financecontrol.budget.domain.model.Receita;
import br.com.financecontrol.budget.domain.repository.ReceitaRepository;
import br.com.financecontrol.budget.domain.service.CadastroReceitaService;
import br.com.financecontrol.budget.util.BudgetAppUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroReceitaServiceImpl implements CadastroReceitaService {

    @Autowired
    private ReceitaRepository repository;

    public ReceitaOutputDTO save(ReceitaInputDTO receita) {
        String descReceitaAtual = receita.getDescricao();
        int mesReceitaAtual = BudgetAppUtil.getMonth(receita.convertToObject());

        if(verificaDescMes(descReceitaAtual, mesReceitaAtual)){
            throw new SameResourceException("Já existe um registro com a mesma descrição para o mesmo mês");
        }
        Receita receitaSalva = repository.save(receita.convertToObject());
        return ReceitaOutputDTO.transformToDTO(receitaSalva);
    }

    public List<ReceitaOutputDTO> findAll(){
        return toListReceitaOutputDTO(repository.findAll());
    }

    public ReceitaOutputDTO findById(Long id){
        Optional<Receita> receitaOptional = repository.findById(id);

        if(receitaOptional.isPresent()) {
            return ReceitaOutputDTO.transformToDTO(receitaOptional.get());
        }
        throw new ResourceNotFoundException(String.format("Não localizado receita com id %s", id));
    }

    public ReceitaOutputDTO update(Long id, ReceitaInputDTO receita) {
        String descReceitaAtual = receita.getDescricao();
        int mesReceitaAtual = BudgetAppUtil.getMonth(receita.convertToObject());
        Optional<Receita> receitaDb = repository.findById(id);
        
        if (receitaDb.isPresent()){
            String descReceitaDb = receitaDb.get().getDescricao();
            int mesReceitaAtualDb = BudgetAppUtil.getMonth(receitaDb.get());

            if(descReceitaAtual.equals(descReceitaDb) && mesReceitaAtual == mesReceitaAtualDb){
                throw new SameResourceException("Já existe um registro com a mesma descrição para o mesmo mês");
            }

            if(verificaDescMes(descReceitaAtual, mesReceitaAtual)){
                throw new SameResourceException("Já existe um registro com a mesma descrição para o mesmo mês");
            }
        }
        BeanUtils.copyProperties(receita, receitaDb.get(), "id");
        repository.save(receitaDb.get());
        return ReceitaOutputDTO.transformToDTO(receitaDb.get());
    }

    public List<ReceitaOutputDTO> findByDesc(String descricao) {
        List<Receita> receitas = repository.findReceitaByDescricao(descricao);
        if(receitas.isEmpty()) throw new ResourceNotFoundException(String.format("Não encontrada receita com descrição %s", descricao));
        return toListReceitaOutputDTO(receitas);
    }

    public List<ReceitaOutputDTO> getDespesaByYearAndMonth(String ano, int mes) {
        String anoMes = ano + "-" + mes;
        List<Receita> receitas = repository.getDespesaByYearAndMonth(anoMes);
        if(receitas.isEmpty()) throw new ResourceNotFoundException(String.format("Não encontrado receita para %d/%s", mes, ano));

        return toListReceitaOutputDTO(receitas);
    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        } catch (Exception e){
            throw new ResourceNotFoundException(String.format("Não encontrada receita com id %s", id));
        }
    }

    //Retorna true se encontrar um registro com a mesma descricao no mesmo mes
    public boolean verificaDescMes(String descReceitaAtual, int mesReceitaAtual ){
        List<Receita> receitas = repository.findAll();

        return receitas.stream()
                .anyMatch(receita -> (descReceitaAtual.equals(receita.getDescricao()) &&
                mesReceitaAtual == BudgetAppUtil.getMonth(receita)));
    }

    public List<ReceitaOutputDTO> toListReceitaOutputDTO(List<Receita> receitas) {
        List<ReceitaOutputDTO> receitaOutput = new ArrayList<>();

        receitas.forEach(receita -> {
            receitaOutput.add(ReceitaOutputDTO.transformToDTO(receita));
        });

        return receitaOutput;
    }
}
