package br.com.financecontrol.budget.domain.repository;

import br.com.financecontrol.budget.domain.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findDespesaByDescricao(String descricao);

    @Query(value = "select *  from despesa d where CONCAT(YEAR(data),'-',MONTH(data)) = ?1", nativeQuery = true)
    List<Despesa> getDespesaByYearAndMonth(@Param("anoMes") String anoMes);
}
