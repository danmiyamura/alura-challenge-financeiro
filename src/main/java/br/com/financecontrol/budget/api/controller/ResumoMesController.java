package br.com.financecontrol.budget.api.controller;

import br.com.financecontrol.budget.domain.dto.output.ResumoMes;
import br.com.financecontrol.budget.domain.service.impl.ResumoMesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumo")
public class ResumoMesController {

    @Autowired
    ResumoMesServiceImpl service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{ano}/{mes}")
    public ResumoMes resumoMensal(@PathVariable String ano, @PathVariable int mes){
        return service.resumoMensal(ano, mes);
    }
}
