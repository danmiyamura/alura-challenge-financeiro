package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.model.Receita;
import br.com.financecontrol.budget.domain.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroReceitaService {

    @Autowired
    private ReceitaRepository repository;

    public Receita save(Receita receita) {
        return repository.save(receita);
    }

    public List<Receita> findAll(){
        return repository.findAll();
    }

    public Receita findById(Long id){
        Optional<Receita> receitaOptional = repository.findById(id);

        if(receitaOptional.isPresent()) {
            return receitaOptional.get();
        }
        return null;
    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
