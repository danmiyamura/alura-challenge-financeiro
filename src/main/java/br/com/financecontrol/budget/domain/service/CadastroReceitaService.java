package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.dto.input.ReceitaInputDTO;
import br.com.financecontrol.budget.domain.dto.output.ReceitaOutputDTO;

import java.util.List;

public interface CadastroReceitaService {
    ReceitaOutputDTO save(ReceitaInputDTO receita);
    List<ReceitaOutputDTO> findAll();
    ReceitaOutputDTO update(Long id, ReceitaInputDTO receitaInputDTO);
    ReceitaOutputDTO findById(Long id);
    List<ReceitaOutputDTO> findByDesc(String descricao);
    List<ReceitaOutputDTO> getDespesaByYearAndMonth(String ano, int mes);
    void delete(Long id);
}
