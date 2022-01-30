package br.com.financecontrol.budget.domain.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalPorCategoria {

    private String categoria;
    private BigDecimal totalCategoria;

}
