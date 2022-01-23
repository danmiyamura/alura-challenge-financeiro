package br.com.financecontrol.budget.util;

import br.com.financecontrol.budget.domain.model.Despesa;
import br.com.financecontrol.budget.domain.model.Receita;

import java.util.Date;

public class BudgetAppUtil {

    public static int getMonth(Receita receita){
        Date data = receita.getData();
        return data.getMonth();
    }

    public static int getMonth(Despesa despesa){
        Date data = despesa.getData();
        return data.getMonth();
    }
}
