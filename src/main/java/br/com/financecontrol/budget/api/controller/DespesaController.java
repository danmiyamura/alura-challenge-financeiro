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

    @GetMapping(params = "descricao")
    public ResponseEntity<List<Despesa>> buscaPorDesc(@RequestParam("descricao") String descricao){
        return service.findByDesc(descricao);
    }

    @GetMapping(value = "/{ano}/{mes}")
    public ResponseEntity<List<Despesa>> buscaPorAnoMes(@PathVariable String ano, @PathVariable int mes){
        return service.getDespesaByYearAndMonth(ano, mes);
    }

    @PostMapping
    public ResponseEntity<Despesa>  salvar(@RequestBody Despesa despesa){
        return service.save(despesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesa> atualizar(@PathVariable Long id, @RequestBody Despesa despesa){
        return service.update(id, despesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Despesa> remover(@PathVariable Long id){
       return service.delete(id);
    }
}
