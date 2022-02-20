package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.dto.output.ResumoMes;

public interface ResumoMesService {
    ResumoMes resumoMensal(String ano, int mes);
}
