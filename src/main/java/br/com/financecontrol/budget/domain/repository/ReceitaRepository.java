package br.com.financecontrol.budget.domain.repository;

import br.com.financecontrol.budget.domain.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findReceitaByDescricao(String descricao);

    @Query(value = "select *  from receita d where CONCAT(YEAR(data),'-',MONTH(data)) = ?1", nativeQuery = true)
    List<Receita> getDespesaByYearAndMonth(@Param("anoMes") String anoMes);
}
