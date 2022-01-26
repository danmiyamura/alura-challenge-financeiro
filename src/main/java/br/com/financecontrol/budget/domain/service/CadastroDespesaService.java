package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.model.CategoriaDespesa;
import br.com.financecontrol.budget.domain.model.Despesa;
import br.com.financecontrol.budget.domain.repository.DespesaRepository;
import br.com.financecontrol.budget.util.BudgetAppUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroDespesaService {

    @Autowired
    private DespesaRepository repository;

    public ResponseEntity<Despesa> save(Despesa despesa) {
        List<Despesa> listaDespesas = findAll();
        String descDespesaAtual = despesa.getDescricao();
        int mesDespesaAtual = BudgetAppUtil.getMonth(despesa);

        if(despesa.getTipo() == null) {
            despesa.setTipo(CategoriaDespesa.OUTRAS.getDescricao());
        }

        if(verificaDescMes(descDespesaAtual, mesDespesaAtual)) {
            return ResponseEntity.badRequest().build();
        }

        repository.save(despesa);
        return ResponseEntity.ok(despesa);
    }

    public List<Despesa> findAll(){
        return repository.findAll();
    }

    public ResponseEntity<Despesa> findById(Long id){
        Optional<Despesa> despesaOptional = repository.findById(id);

        if(despesaOptional.isPresent()) {
            return ResponseEntity.ok(despesaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Despesa> delete(Long id){
        try{
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Despesa> update(Long id, Despesa despesa) {
        String descDespesaAtual = despesa.getDescricao();
        int mesDespesaAtual = BudgetAppUtil.getMonth(despesa);
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

        if(despesa.getTipo() == null){
            despesa.setTipo(despesaDb.get().getTipo());
        }
        BeanUtils.copyProperties(despesa, despesaDb.get(), "id");
        repository.save(despesaDb.get());
        return ResponseEntity.ok(despesaDb.get());
    }

    public ResponseEntity<List<Despesa>> findByDesc(String descricao) {
        List<Despesa> despesas = repository.findDespesaByDescricao(descricao);
        if(despesas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(despesas);
    }

    //Retorna true se encontrar um registro com a mesma descricao no mesmo mes
    public boolean verificaDescMes(String descDespesaAtual, int mesDespesaAtual ){
        List<Despesa> despesasDb = findAll();
        for (Despesa despesaDB : despesasDb) {
            String descDb = despesaDB.getDescricao();
            int mesDb = BudgetAppUtil.getMonth(despesaDB);

            if(descDespesaAtual.equals(descDb) && mesDespesaAtual == mesDb) {
                return true;
            }
        }
        return false;
    }
}
