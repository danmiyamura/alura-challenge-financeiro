package br.com.financecontrol.budget.api.controller;

import br.com.financecontrol.budget.domain.dto.input.DespesaInputDTO;
import br.com.financecontrol.budget.domain.dto.output.DespesaOutputDTO;
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
    public List<DespesaOutputDTO> listar(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaOutputDTO> busca(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping(params = "descricao")
    public ResponseEntity<List<DespesaOutputDTO>> buscaPorDesc(@RequestParam("descricao") String descricao){
        return service.findByDesc(descricao);
    }

    @GetMapping(value = "/{ano}/{mes}")
    public ResponseEntity<List<DespesaOutputDTO>> buscaPorAnoMes(@PathVariable String ano, @PathVariable int mes){
        return service.getDespesaByYearAndMonth(ano, mes);
    }

    @PostMapping
    public ResponseEntity<DespesaOutputDTO> salvar(@RequestBody DespesaInputDTO despesa){
        return service.save(despesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaOutputDTO> atualizar(@PathVariable Long id, @RequestBody DespesaInputDTO despesa){
        return service.update(id, despesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DespesaOutputDTO> remover(@PathVariable Long id){
       return service.delete(id);
    }
}
