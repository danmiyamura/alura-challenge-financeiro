package br.com.financecontrol.budget.domain.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiError {
    private int status;
    private String message;
}
