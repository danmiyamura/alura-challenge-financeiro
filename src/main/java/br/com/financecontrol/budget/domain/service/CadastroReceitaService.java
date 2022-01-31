package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.dto.input.ReceitaInputDTO;
import br.com.financecontrol.budget.domain.dto.output.ReceitaOutputDTO;
import br.com.financecontrol.budget.domain.model.Receita;
import br.com.financecontrol.budget.domain.repository.ReceitaRepository;
import br.com.financecontrol.budget.util.BudgetAppUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroReceitaService {

    @Autowired
    private ReceitaRepository repository;

    public ResponseEntity<ReceitaOutputDTO> save(ReceitaInputDTO receita) {
        String descReceitaAtual = receita.getDescricao();
        int mesReceitaAtual = BudgetAppUtil.getMonth(receita.convertToObject());

        if(verificaDescMes(descReceitaAtual, mesReceitaAtual)){
            return ResponseEntity.badRequest().build();
        }
        Receita receitaSalva = repository.save(receita.convertToObject());
        return ResponseEntity.ok(ReceitaOutputDTO.transformToDTO(receitaSalva));
    }

    public List<ReceitaOutputDTO> findAll(){
        return toListReceitaOutputDTO(repository.findAll());
    }

    public ResponseEntity<ReceitaOutputDTO> findById(Long id){
        Optional<Receita> receitaOptional = repository.findById(id);

        if(receitaOptional.isPresent()) {
            return ResponseEntity.ok(ReceitaOutputDTO.transformToDTO(receitaOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ReceitaOutputDTO> update(Long id, ReceitaInputDTO receita) {
        String descReceitaAtual = receita.getDescricao();
        int mesReceitaAtual = BudgetAppUtil.getMonth(receita.convertToObject());
        Optional<Receita> receitaDb = repository.findById(id);
        
        if (receitaDb.isPresent()){
            String descReceitaDb = receitaDb.get().getDescricao();
            int mesReceitaAtualDb = BudgetAppUtil.getMonth(receitaDb.get());

            if(descReceitaAtual.equals(descReceitaDb) && mesReceitaAtual == mesReceitaAtualDb){
                return ResponseEntity.badRequest().build();
            }

            if(verificaDescMes(descReceitaAtual, mesReceitaAtual)){
                return ResponseEntity.badRequest().build();
            }
        }
        BeanUtils.copyProperties(receita, receitaDb.get(), "id");
        repository.save(receitaDb.get());
        return ResponseEntity.ok(ReceitaOutputDTO.transformToDTO(receitaDb.get()));
    }

    public ResponseEntity<List<ReceitaOutputDTO>> findByDesc(String descricao) {
        List<Receita> receitas = repository.findReceitaByDescricao(descricao);
        if(receitas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toListReceitaOutputDTO(receitas));
    }

    public ResponseEntity<List<ReceitaOutputDTO>> getDespesaByYearAndMonth(String ano, int mes) {
        String anoMes = ano + "-" + mes;
        List<Receita> receitas = repository.getDespesaByYearAndMonth(anoMes);
        if(receitas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toListReceitaOutputDTO(receitas));
    }

    public ResponseEntity<Receita> delete(Long id){
        try{
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
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
