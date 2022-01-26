package br.com.financecontrol.budget.domain.repository;

import br.com.financecontrol.budget.domain.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findReceitaByDescricao(String descricao);
}
