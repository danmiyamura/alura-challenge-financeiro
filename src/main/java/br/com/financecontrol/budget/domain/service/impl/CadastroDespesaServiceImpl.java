package br.com.financecontrol.budget.domain.service.impl;

import br.com.financecontrol.budget.domain.dto.input.DespesaInputDTO;
import br.com.financecontrol.budget.domain.dto.output.DespesaOutputDTO;
import br.com.financecontrol.budget.domain.exception.ResourceNotFoundException;
import br.com.financecontrol.budget.domain.exception.SameResourceException;
import br.com.financecontrol.budget.domain.model.Despesa;
import br.com.financecontrol.budget.domain.repository.DespesaRepository;
import br.com.financecontrol.budget.domain.service.CadastroDespesaService;
import br.com.financecontrol.budget.util.BudgetAppUtil;
import br.com.financecontrol.budget.util.CategoriaDespesa;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroDespesaServiceImpl implements CadastroDespesaService {

    @Autowired
    private DespesaRepository repository;

    @Override
    public DespesaOutputDTO save(DespesaInputDTO despesa) {
        String descDespesaAtual = despesa.getDescricao();
        int mesDespesaAtual = BudgetAppUtil.getMonth(despesa.convertToObjetc());

        if(despesa.getCategoria() == null) {
            despesa.setCategoria(CategoriaDespesa.OUTRAS.getDescricao());
        }

        if(verificaDescMes(descDespesaAtual, mesDespesaAtual)) {
            throw new SameResourceException("Já existe um registro com a mesma descrição para o mesmo mês");
        }

        return DespesaOutputDTO.transformToDTO(repository.save(despesa.convertToObjetc()));
    }

    @Override
    public List<DespesaOutputDTO> findAll(){
        return toListDespesaOutputDTO(repository.findAll());
    }

    @Override
    public DespesaOutputDTO findById(Long id){
        return DespesaOutputDTO.transformToDTO(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Não encontrado despesa com id %s", id))));
    }

    @Override
    public void delete(Long id){
        try{
            repository.deleteById(id);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResourceNotFoundException(String.format("Despesa com id %s não encontrada", id));
        }
    }

    @Override
    public DespesaOutputDTO update(Long id, DespesaInputDTO despesa) {
        String descDespesaAtual = despesa.getDescricao();
        int mesDespesaAtual = BudgetAppUtil.getMonth(despesa.convertToObjetc());
        Optional<Despesa> despesaDb = repository.findById(id);

        if (despesaDb.isPresent()){
            String descDespesaDb = despesaDb.get().getDescricao();
            int mesDespesaAtualDb = BudgetAppUtil.getMonth(despesaDb.get());

            if(descDespesaAtual.equals(descDespesaDb) && mesDespesaAtual == mesDespesaAtualDb){
                throw new SameResourceException("Já existe um registro com a mesma descrição para o mesmo mês");
            }
        }
        if(verificaDescMes(descDespesaAtual, mesDespesaAtual)){
            throw new SameResourceException("Já existe um registro com a mesma descrição para o mesmo mês");
        }

        if(despesa.getCategoria() == null){
            despesa.setCategoria(despesaDb.get().getCategoria());
        }

        BeanUtils.copyProperties(despesa, despesaDb.get(), "id");
        repository.save(despesaDb.get());
        return DespesaOutputDTO.transformToDTO(despesaDb.get());
    }

    @Override
    public List<DespesaOutputDTO> findByDesc(String descricao) {
        List<Despesa> despesas = repository.findDespesaByDescricao(descricao);
        if(despesas.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Não localizada despesa com descrição: %s", descricao));
        }
        return toListDespesaOutputDTO(despesas);
    }

    @Override
    public List<DespesaOutputDTO> getDespesaByYearAndMonth(String ano, int mes) {
        String anoMes = ano + "-" + mes;
        List<Despesa> despesas = repository.getDespesaByYearAndMonth(anoMes);
        if(despesas.isEmpty()){
            throw new ResourceNotFoundException(String.format("Não encontradao despesas para data %d/%s", mes, ano));
        }
        return toListDespesaOutputDTO(despesas);
    }

    //Retorna true se encontrar um registro com a mesma descricao no mesmo mes
    public boolean verificaDescMes(String descDespesaAtual, int mesDespesaAtual ) {
        List<Despesa> despesas = repository.findAll();

        return despesas.stream().anyMatch(despesa ->
                (descDespesaAtual.equals(despesa.getDescricao()) &&
                        mesDespesaAtual == BudgetAppUtil.getMonth(despesa)));
    }

    public List<DespesaOutputDTO> toListDespesaOutputDTO(List<Despesa> despesas){
        List<DespesaOutputDTO> despesaOutputDTO = new ArrayList<>();

        despesas.forEach(despesa -> {
            despesaOutputDTO.add(DespesaOutputDTO.transformToDTO(despesa));
        });

        return despesaOutputDTO;
    }
}
