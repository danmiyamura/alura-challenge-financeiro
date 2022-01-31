package br.com.financecontrol.budget.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;

    public Receita(String descricao, BigDecimal valor, Date data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }
}
