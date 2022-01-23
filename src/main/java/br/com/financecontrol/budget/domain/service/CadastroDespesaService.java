package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.model.Despesa;
import br.com.financecontrol.budget.domain.model.Despesa;
import br.com.financecontrol.budget.domain.repository.DespesaRepository;
import br.com.financecontrol.budget.util.BudgetAppUtil;
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

        for (Despesa receitaDB : listaDespesas) {
            String descDb = receitaDB.getDescricao();
            int mesDb = BudgetAppUtil.getMonth(receitaDB);

            if(descDespesaAtual.equals(descDb) && mesDespesaAtual == mesDb) {
                return ResponseEntity.badRequest().build();
            }
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
}
