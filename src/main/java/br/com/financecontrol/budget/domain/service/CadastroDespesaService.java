package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.dto.input.DespesaInputDTO;
import br.com.financecontrol.budget.domain.dto.output.DespesaOutputDTO;
import br.com.financecontrol.budget.domain.model.Despesa;
import br.com.financecontrol.budget.domain.repository.DespesaRepository;
import br.com.financecontrol.budget.util.BudgetAppUtil;
import br.com.financecontrol.budget.util.CategoriaDespesa;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroDespesaService {

    @Autowired
    private DespesaRepository repository;

    public ResponseEntity<DespesaOutputDTO> save(DespesaInputDTO despesa) {
        List<Despesa> listaDespesas = repository.findAll();
        String descDespesaAtual = despesa.getDescricao();
        int mesDespesaAtual = BudgetAppUtil.getMonth(despesa.convertToObjetc());

        if(despesa.getCategoria() == null) {
            despesa.setCategoria(CategoriaDespesa.OUTRAS.getDescricao());
        }

        if(verificaDescMes(descDespesaAtual, mesDespesaAtual)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(DespesaOutputDTO.transformToDTO(repository.save(despesa.convertToObjetc())));
    }

    public List<DespesaOutputDTO> findAll(){
        return toListDespesaOutputDTO(repository.findAll());
    }

    public ResponseEntity<DespesaOutputDTO> findById(Long id){
        Optional<Despesa> despesaOptional = repository.findById(id);

        if(despesaOptional.isPresent()) {
            return ResponseEntity.ok(DespesaOutputDTO.transformToDTO(despesaOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<DespesaOutputDTO> delete(Long id){
        try{
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<DespesaOutputDTO> update(Long id, DespesaInputDTO despesa) {
        String descDespesaAtual = despesa.getDescricao();
        int mesDespesaAtual = BudgetAppUtil.getMonth(despesa.convertToObjetc());
        Optional<Despesa> despesaDb = repository.findById(id);

        if (despesaDb.isPresent()){
            String descDespesaDb = despesaDb.get().getDescricao();
            int mesDespesaAtualDb = BudgetAppUtil.getMonth(despesaDb.get());

            if(descDespesaAtual.equals(descDespesaDb) && mesDespesaAtual == mesDespesaAtualDb){
                return ResponseEntity.badRequest().build();
            }
        }
        if(verificaDescMes(descDespesaAtual, mesDespesaAtual)){
            return ResponseEntity.badRequest().build();
        }

        if(despesa.getCategoria() == null){
            despesa.setCategoria(despesaDb.get().getCategoria());
        }

        BeanUtils.copyProperties(despesa, despesaDb.get(), "id");
        repository.save(despesaDb.get());
        return ResponseEntity.ok(DespesaOutputDTO.transformToDTO(despesaDb.get()));
    }

    public ResponseEntity<List<DespesaOutputDTO>> findByDesc(String descricao) {
        List<Despesa> despesas = repository.findDespesaByDescricao(descricao);
        if(despesas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toListDespesaOutputDTO(despesas));
    }

    public ResponseEntity<List<DespesaOutputDTO>> getDespesaByYearAndMonth(String ano, int mes) {
        String anoMes = ano + "-" + mes;
        List<Despesa> despesas = repository.getDespesaByYearAndMonth(anoMes);
        if(despesas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toListDespesaOutputDTO(despesas));
    }

    //Retorna true se encontrar um registro com a mesma descricao no mesmo mes
    public boolean verificaDescMes(String descDespesaAtual, int mesDespesaAtual ) {
        List<Despesa> despesas = repository.findAll();

        return despesas.stream().anyMatch(despesa ->
                (descDespesaAtual.equals(despesa.getDescricao()) &&
                        mesDespesaAtual == BudgetAppUtil.getMonth(despesa)));
    }

    public List<DespesaOutputDTO> toListDespesaOutputDTO(List<Despesa> despesas){
        List<DespesaOutputDTO> despesaOutputDTOS = new ArrayList<>();

        despesas.forEach(despesa -> {
            despesaOutputDTOS.add(DespesaOutputDTO.transformToDTO(despesa));
        });

        return despesaOutputDTOS;
    }
}
