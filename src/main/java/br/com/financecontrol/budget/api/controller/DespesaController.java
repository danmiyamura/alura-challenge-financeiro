package br.com.financecontrol.budget.api.controller;

import br.com.financecontrol.budget.domain.model.Despesa;
import br.com.financecontrol.budget.domain.service.CadastroDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/despesas")
public class DespesaController {

    @Autowired
    private CadastroDespesaService service;

    @GetMapping
    public List<Despesa> listar(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> busca(@PathVariable Long id){
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Despesa> remover(@PathVariable Long id){
       return service.delete(id);
    }
}
