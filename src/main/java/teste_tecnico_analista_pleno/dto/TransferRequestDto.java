package teste_tecnico_analista_pleno.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDto {
    private String originAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private LocalDate transferDate;
}
