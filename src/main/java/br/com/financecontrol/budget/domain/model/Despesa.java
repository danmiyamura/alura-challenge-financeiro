package br.com.financecontrol.budget.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    private String categoria;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;

    public Despesa(String descricao, BigDecimal valor, String categoria, Date data) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
    }
}
