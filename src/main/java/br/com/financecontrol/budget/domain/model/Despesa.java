package br.com.financecontrol.budget.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Long id;

    @Column(nullable = false)
    String descricao;

    @Column(nullable = false)
    BigDecimal valor;

    String tipo;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date data;
}
