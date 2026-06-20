package teste_tecnico_analista_pleno.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferResponseDto {
    private Long id; 
    private String originAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private BigDecimal fee;
    private LocalDate transferDate;
    private LocalDateTime appointmentDate;
}
