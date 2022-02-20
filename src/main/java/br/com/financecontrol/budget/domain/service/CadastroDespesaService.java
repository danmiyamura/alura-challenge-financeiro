package br.com.financecontrol.budget.domain.service;

import br.com.financecontrol.budget.domain.dto.input.DespesaInputDTO;
import br.com.financecontrol.budget.domain.dto.output.DespesaOutputDTO;

import java.util.List;

public interface CadastroDespesaService {
    DespesaOutputDTO save(DespesaInputDTO despesaInputDTO);

    List<DespesaOutputDTO> findAll();

    DespesaOutputDTO findById(Long id);

    void delete(Long id);

    DespesaOutputDTO update(Long id, DespesaInputDTO despesa);

    List<DespesaOutputDTO> findByDesc(String descricao);

    List<DespesaOutputDTO> getDespesaByYearAndMonth(String ano, int mes);
}
