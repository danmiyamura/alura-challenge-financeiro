package br.com.financecontrol.budget.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Long id;

    @Column(nullable = false)
    String descricao;

    @Column(nullable = false)
    BigDecimal valor;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date data;

}
