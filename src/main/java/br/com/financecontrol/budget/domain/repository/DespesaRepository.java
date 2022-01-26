package br.com.financecontrol.budget.domain.repository;

import br.com.financecontrol.budget.domain.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findDespesaByDescricao(String descricao);
}
