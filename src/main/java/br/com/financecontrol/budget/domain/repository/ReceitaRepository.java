package br.com.financecontrol.budget.domain.repository;

import br.com.financecontrol.budget.domain.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
