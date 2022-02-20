package br.com.financecontrol.budget.domain.service.impl;

import br.com.financecontrol.budget.domain.dto.output.ResumoMes;
import br.com.financecontrol.budget.domain.dto.output.TotalPorCategoria;
import br.com.financecontrol.budget.domain.model.Despesa;
import br.com.financecontrol.budget.domain.model.Receita;
import br.com.financecontrol.budget.domain.repository.DespesaRepository;
import br.com.financecontrol.budget.domain.repository.ReceitaRepository;
import br.com.financecontrol.budget.domain.service.ResumoMesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ResumoMesServiceImpl implements ResumoMesService {
    /*
    Valor total das receitas no mês check
    Valor total das despesas no mês check
    Saldo final no mês
    Valor total gasto no mês em cada uma das categorias
    */

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    public ResumoMes resumoMensal(String ano, int mes) {
        String anoMes = ano + "-" + mes;
        ResumoMes resumoMes = new ResumoMes(valorTotalReceitaMes(anoMes), valorTotalDespesa(anoMes),
                saldoFinalMes(anoMes), totalPorCategorias(anoMes));
        return resumoMes;
    }

    public BigDecimal valorTotalReceitaMes(String anoMes){
        BigDecimal totalReceita =  BigDecimal.ZERO;
        try {
            List<Receita> receitas = receitaRepository.getDespesaByYearAndMonth(anoMes);
            totalReceita = receitas.stream()
                    .map(Receita::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return totalReceita;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public BigDecimal valorTotalDespesa(String anoMes){
        BigDecimal totalDespesa = BigDecimal.ZERO;
        try{
            List<Despesa> despesas = despesaRepository.getDespesaByYearAndMonth(anoMes);
            totalDespesa = despesas.stream()
                    .map(Despesa::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return totalDespesa;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public BigDecimal saldoFinalMes(String anoMes){
        return valorTotalReceitaMes(anoMes).subtract(valorTotalDespesa(anoMes));
    }

    public List<TotalPorCategoria> totalPorCategorias(String anoMes){
        List<TotalPorCategoria> totalPorCategorias = new ArrayList<>();
        HashMap<String, BigDecimal> hashMapTotalPorCategorias = new HashMap<String, BigDecimal>();

        try{
            List<Despesa> despesasMes = despesaRepository.getDespesaByYearAndMonth(anoMes);

            despesasMes.forEach(despesa -> {
                if(hashMapTotalPorCategorias.containsKey(despesa.getCategoria())) {
                    BigDecimal aux = despesa.getValor();
                    BigDecimal totalDespesa = hashMapTotalPorCategorias.get(despesa.getCategoria());
                    hashMapTotalPorCategorias.put(despesa.getCategoria(), totalDespesa.add(aux));
                } else{
                    hashMapTotalPorCategorias.put(despesa.getCategoria(), despesa.getValor());
                }
            });
            System.out.println(hashMapTotalPorCategorias);

            hashMapTotalPorCategorias.forEach((categoria, valor) -> {
                totalPorCategorias.add(new TotalPorCategoria(categoria, valor));
            });

            return totalPorCategorias;

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
