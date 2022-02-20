package br.com.financecontrol.budget.api.controller;

import br.com.financecontrol.budget.domain.dto.input.ReceitaInputDTO;
import br.com.financecontrol.budget.domain.dto.output.ReceitaOutputDTO;
import br.com.financecontrol.budget.domain.model.Receita;
import br.com.financecontrol.budget.domain.service.impl.CadastroReceitaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/receitas")
public class ReceitaController {

    @Autowired
    CadastroReceitaServiceImpl service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ReceitaOutputDTO> listar(){
        return service.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ReceitaOutputDTO buscar(@PathVariable Long id){
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "descricao")
    public List<ReceitaOutputDTO> buscarPorDec(@RequestParam String descricao){
        return service.findByDesc(descricao);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{ano}/{mes}")
    public List<ReceitaOutputDTO> buscaPorAnoMes(@PathVariable String ano, @PathVariable int mes){
        return service.getDespesaByYearAndMonth(ano, mes);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReceitaOutputDTO salvar(@RequestBody ReceitaInputDTO receita){
        return service.save(receita);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ReceitaOutputDTO atualizar(@PathVariable Long id, @RequestBody ReceitaInputDTO receita) {
        return service.update(id, receita);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id){
        service.delete(id);
    }
}
