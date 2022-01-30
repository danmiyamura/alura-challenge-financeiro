package br.com.financecontrol.budget.api.controller;

import br.com.financecontrol.budget.domain.dto.output.ResumoMes;
import br.com.financecontrol.budget.domain.service.ResumoMesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumo")
public class ResumoMesController {

    @Autowired
    ResumoMesService service;

    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<ResumoMes> resumoMensal(@PathVariable String ano, @PathVariable int mes){
        return service.resumoMensal(ano, mes);
    }
}
