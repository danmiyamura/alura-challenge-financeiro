package br.com.financecontrol.budget.api.controller;

import br.com.financecontrol.budget.domain.dto.input.DespesaInputDTO;
import br.com.financecontrol.budget.domain.dto.output.DespesaOutputDTO;
import br.com.financecontrol.budget.domain.service.impl.CadastroDespesaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/despesas")
public class DespesaController {

    @Autowired
    private CadastroDespesaServiceImpl service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<DespesaOutputDTO> listar(){
        return service.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public DespesaOutputDTO busca(@PathVariable Long id){
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "descricao")
    public List<DespesaOutputDTO> buscaPorDesc(@RequestParam("descricao") String descricao){
        return service.findByDesc(descricao);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{ano}/{mes}")
    public List<DespesaOutputDTO> buscaPorAnoMes(@PathVariable String ano, @PathVariable int mes){
        return service.getDespesaByYearAndMonth(ano, mes);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public DespesaOutputDTO salvar(@RequestBody DespesaInputDTO despesa){
        return service.save(despesa);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public DespesaOutputDTO atualizar(@PathVariable Long id, @RequestBody DespesaInputDTO despesa){
        return service.update(id, despesa);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id){
       service.delete(id);
    }
}
