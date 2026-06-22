package teste_tecnico_analista_pleno.service.fee;

import java.math.BigDecimal;

public class RangeFeeStrategy implements FeeStrategy {
    private final long minDays;
    private final long maxDays;
    private final BigDecimal rate;

    public RangeFeeStrategy(long minDays, long maxDays, BigDecimal rate) {
        this.minDays = minDays;
        this.maxDays = maxDays;
        this.rate = rate;
    }

    @Override
    public boolean supports(long days) {
        return days >= minDays && days <= maxDays;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(rate);
    }
}
