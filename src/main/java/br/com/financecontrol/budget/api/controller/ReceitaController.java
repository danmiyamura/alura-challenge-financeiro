package br.com.financecontrol.budget.api.controller;

import br.com.financecontrol.budget.domain.model.Receita;
import br.com.financecontrol.budget.domain.service.CadastroReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/receitas")
public class ReceitaController {

    @Autowired
    CadastroReceitaService service;

    @GetMapping
    public List<Receita> listar(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Receita buscar(@PathVariable Long id){
        return service.findById(id);
    }
}
