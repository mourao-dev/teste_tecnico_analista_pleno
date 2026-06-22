package teste_tecnico_analista_pleno.service.fee;

import java.math.BigDecimal;

public class RangeFeeStrategy implements FeeStrategy {
    private final long minDays;
    private final long maxDays;
    private final BigDecimal rate;
    private final BigDecimal fixedFee;

    public RangeFeeStrategy(long minDays, long maxDays, BigDecimal rate, BigDecimal fixedFee) {
        this.minDays = minDays;
        this.maxDays = maxDays;
        this.rate = rate;
        this.fixedFee = fixedFee;
    }

    @Override
    public boolean supports(long days) {
        return days >= minDays && days <= maxDays;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        BigDecimal percentage = amount.multiply(rate);
        return percentage.add(fixedFee != null ? fixedFee : BigDecimal.ZERO);
    }
}
