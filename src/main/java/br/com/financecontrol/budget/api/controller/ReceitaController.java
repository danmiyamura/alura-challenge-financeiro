package br.com.financecontrol.budget.api.controller;

import br.com.financecontrol.budget.domain.model.Receita;
import br.com.financecontrol.budget.domain.service.CadastroReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Receita> buscar(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping(params = "descricao")
    public ResponseEntity<List<Receita>> buscarPorDec(@RequestParam String descricao){
        return service.findByDesc(descricao);
    }

    @GetMapping(value = "/{ano}/{mes}")
    public ResponseEntity<List<Receita>> buscaPorAnoMes(@PathVariable String ano, @PathVariable int mes){
        return service.getDespesaByYearAndMonth(ano, mes);
    }

    @PostMapping
    public ResponseEntity<Receita> salvar(@RequestBody Receita receita){
        return service.save(receita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receita> atualizar(@PathVariable Long id, @RequestBody Receita receita) {
        return service.update(id, receita);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Receita> remover(@PathVariable Long id){
        return service.delete(id);
    }
}
