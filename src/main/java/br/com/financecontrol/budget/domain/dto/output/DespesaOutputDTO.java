package br.com.financecontrol.budget.domain.dto.output;

import br.com.financecontrol.budget.domain.model.Despesa;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DespesaOutputDTO {

    private String descricao;
    private BigDecimal valor;
    private String categoria;
    private Date data;

    public static DespesaOutputDTO transformToDTO(Despesa despesa) {
        return new DespesaOutputDTO(despesa.getDescricao(), despesa.getValor(),
                despesa.getCategoria(), despesa.getData());
    }
}
