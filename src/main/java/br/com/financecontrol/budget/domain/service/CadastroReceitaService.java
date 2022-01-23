package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.model.Receita;
import br.com.financecontrol.budget.domain.repository.ReceitaRepository;
import br.com.financecontrol.budget.util.BudgetAppUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroReceitaService {

    @Autowired
    private ReceitaRepository repository;

    public ResponseEntity<Receita> save(Receita receita) {
        List<Receita> listReceitas = findAll();
        String descReceitaAtual = receita.getDescricao();
        int mesReceitaAtual = BudgetAppUtil.getMonth(receita);

        for (Receita receitaDB : listReceitas) {
            String descDb = receitaDB.getDescricao();
            int mesDb = BudgetAppUtil.getMonth(receitaDB);

            if(descReceitaAtual.equals(descDb) && mesReceitaAtual == mesDb) {
                return ResponseEntity.badRequest().build();
            }
        }
        repository.save(receita);
        return ResponseEntity.ok(receita);
    }

    public List<Receita> findAll(){
        return repository.findAll();
    }

    public ResponseEntity<Receita> findById(Long id){
        Optional<Receita> receitaOptional = repository.findById(id);

        if(receitaOptional.isPresent()) {
            return ResponseEntity.ok(receitaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Receita> update(Long id, Receita receita) {
        String descReceitaAtual = receita.getDescricao();
        int mesReceitaAtual = BudgetAppUtil.getMonth(receita);
        Optional<Receita> receitaDb = repository.findById(id);
        
        if (receitaDb.isPresent()){
            String descReceitaDb = receitaDb.get().getDescricao();
            int mesReceitaAtualDb = BudgetAppUtil.getMonth(receitaDb.get());

            if(descReceitaAtual.equals(descReceitaDb) && mesReceitaAtual == mesReceitaAtualDb){
                return ResponseEntity.badRequest().build();
            }

            List<Receita> receitasDb = findAll();
            for (Receita receitaDB : receitasDb) {
                String descDb = receitaDB.getDescricao();
                int mesDb = BudgetAppUtil.getMonth(receitaDB);

                if(descReceitaAtual.equals(descDb) && mesReceitaAtual == mesDb) {
                    return ResponseEntity.badRequest().build();
                }
            }
        }

        BeanUtils.copyProperties(receita, receitaDb.get(), "id");
        repository.save(receitaDb.get());
        return ResponseEntity.ok(receitaDb.get());
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
}
