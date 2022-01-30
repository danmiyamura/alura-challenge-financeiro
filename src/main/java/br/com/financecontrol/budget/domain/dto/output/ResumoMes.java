package br.com.financecontrol.budget.domain.dto.output;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumoMes {

    private BigDecimal totalReceita;
    private BigDecimal totalDespesa;
    private BigDecimal saldoFinalMes;
    private List<TotalPorCategoria> totalPorCategoria;

}
