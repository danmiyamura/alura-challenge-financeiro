package br.com.financecontrol.budget.domain.dto.input;

import br.com.financecontrol.budget.domain.model.Despesa;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class DespesaInputDTO {

    private String descricao;
    private BigDecimal valor;
    private String categoria;
    private Date data;

    public Despesa convertToObjetc() {
        return new Despesa(descricao, valor, categoria, data);
    }
}
