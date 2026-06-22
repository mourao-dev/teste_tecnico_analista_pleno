package teste_tecnico_analista_pleno.service.fee;

import java.math.BigDecimal;
import java.util.List;

import teste_tecnico_analista_pleno.handler.exceptions.FeeNotApplicableException;

public class FeeCalculator {
private final List<FeeStrategy> strategies = List.of(
        new RangeFeeStrategy(0, 0, new BigDecimal("0.025"), new BigDecimal("3.00")),
        new RangeFeeStrategy(1, 10, BigDecimal.ZERO, new BigDecimal("12.00")),
        new RangeFeeStrategy(11, 20, new BigDecimal("0.082"), BigDecimal.ZERO),
        new RangeFeeStrategy(21, 30, new BigDecimal("0.069"), BigDecimal.ZERO),
        new RangeFeeStrategy(31, 40, new BigDecimal("0.047"), BigDecimal.ZERO),
        new RangeFeeStrategy(41, 50, new BigDecimal("0.017"), BigDecimal.ZERO)
);

    public BigDecimal calculate(long days, BigDecimal amount) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(days))
                .findFirst()
                .orElseThrow(() -> new FeeNotApplicableException("Transferência não autorizada. Taxa não aplicável"))
                .calculate(amount);
    }
}
