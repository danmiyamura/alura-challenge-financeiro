package br.com.financecontrol.budget.domain.model;

import lombok.Getter;

@Getter
public enum CategoriaDespesa {
    ALIMENTACAO("Alimentação"),
    MORADIA("Moradia"),
    TRANSPORTE("Transporte"),
    SAUDE("Saúde"),
    EDUCACAO("Educação"),
    LAZER("Lazer"),
    IMPREVISTOS("Imprevistos"),
    OUTRAS("Outras");

    private String descricao;

    CategoriaDespesa(String descricao){
        this.descricao = descricao;
    }
}
