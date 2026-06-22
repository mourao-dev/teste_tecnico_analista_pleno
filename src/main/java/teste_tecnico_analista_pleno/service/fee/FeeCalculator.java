package teste_tecnico_analista_pleno.service.fee;

import java.math.BigDecimal;
import java.util.List;

import teste_tecnico_analista_pleno.handler.exceptions.FeeNotApplicableException;

public class FeeCalculator {
    private final List<FeeStrategy> strategies = List.of(
            new RangeFeeStrategy(0, 0, new BigDecimal("0.025")),
            new RangeFeeStrategy(1, 10, BigDecimal.ZERO),
            new RangeFeeStrategy(11, 20, new BigDecimal("0.082")),
            new RangeFeeStrategy(21, 30, new BigDecimal("0.069")),
            new RangeFeeStrategy(31, 40, new BigDecimal("0.047")),
            new RangeFeeStrategy(41, 50, new BigDecimal("0.017"))
    );

    public BigDecimal calculate(long days, BigDecimal amount) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(days))
                .findFirst()
                .orElseThrow(() -> new FeeNotApplicableException("Transfer not allowed. Fee not applicable"))
                .calculate(amount);
    }
}
