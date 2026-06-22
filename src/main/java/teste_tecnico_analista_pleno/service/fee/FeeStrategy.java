package teste_tecnico_analista_pleno.service.fee;

import java.math.BigDecimal;

public interface FeeStrategy {
    boolean supports(long days);

    BigDecimal calculate(BigDecimal amount);
}
