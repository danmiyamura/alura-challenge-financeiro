package br.com.financecontrol.budget.domain.dto.output;

import br.com.financecontrol.budget.domain.model.Receita;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceitaOutputDTO {

    private String descricao;
    private BigDecimal valor;
    private Date data;

    public static ReceitaOutputDTO transformToDTO(Receita receita){
        return new ReceitaOutputDTO(receita.getDescricao(), receita.getValor(), receita.getData());
    }
}
