package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.model.Despesa;
import br.com.financecontrol.budget.domain.model.Receita;
import br.com.financecontrol.budget.domain.repository.DespesaRepository;
import br.com.financecontrol.budget.domain.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroDespesaService {
    @Autowired
    private DespesaRepository repository;

    public Despesa save(Despesa despesa) {
        return repository.save(despesa);
    }

    public List<Despesa> findAll(){
        return repository.findAll();
    }

    public Despesa findById(Long id){
        Optional<Despesa> despesaOptional = repository.findById(id);

        if(despesaOptional.isPresent()) {
            return despesaOptional.get();
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
