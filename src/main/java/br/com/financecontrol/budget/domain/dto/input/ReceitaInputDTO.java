package br.com.financecontrol.budget.domain.dto.input;

import br.com.financecontrol.budget.domain.model.Receita;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class ReceitaInputDTO {

    private String descricao;
    private BigDecimal valor;
    private Date data;

    public Receita convertToObject(){
        return new Receita(descricao, valor, data);
    }
}
