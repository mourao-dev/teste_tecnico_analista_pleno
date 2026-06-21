package teste_tecnico_analista_pleno.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import teste_tecnico_analista_pleno.domain.Transfer;
import teste_tecnico_analista_pleno.dto.TransferRequestDto;
import teste_tecnico_analista_pleno.dto.TransferResponseDto;
import teste_tecnico_analista_pleno.handler.exceptions.FeeNotApplicableException;
import teste_tecnico_analista_pleno.repository.TransferRepository;

@Service
public class TransferService implements TransferServiceInterface {
       private final TransferRepository repository;

    public TransferService(TransferRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransferResponseDto create(TransferRequestDto request) {

        Transfer transfer = new Transfer();

        transfer.setOriginAccount(request.getOriginAccount());
        transfer.setDestinationAccount(request.getDestinationAccount());
        transfer.setAmount(request.getAmount());
        transfer.setTransferDate(request.getTransferDate());
        transfer.setAppointmentDate(LocalDate.now());
        BigDecimal fee = calculateFee(transfer);
        transfer.setFee(fee);

        repository.save(transfer);

        return new TransferResponseDto();
    }

    private TransferResponseDto toResponse(Transfer transfer) {

    TransferResponseDto dto = new TransferResponseDto();

    dto.setId(transfer.getId());
    dto.setOriginAccount(transfer.getOriginAccount());
    dto.setDestinationAccount(transfer.getDestinationAccount());
    dto.setAmount(transfer.getAmount());
    dto.setFee(transfer.getFee());
    dto.setTransferDate(transfer.getTransferDate());

    return dto;
}

   @Override
public List<TransferResponseDto> getByAccount(String account) {

    return repository.findByOriginAccount(account)
            .stream()
            .map(this::toResponse)
            .toList();
}

public BigDecimal calculateFee(Transfer transfer) {

    long days = ChronoUnit.DAYS.between(
            transfer.getAppointmentDate(),
            transfer.getTransferDate()
    );

    if (days < 0 || days > 50) {
        throw new FeeNotApplicableException("Transfer not allowed. Fee not applicable");
    }

    if (days == 0) {
        return new BigDecimal("0.025");
    }

    if (days >= 1 && days <= 10) {
        return BigDecimal.ZERO;
    }

    if (days >= 11 && days <= 20) {
        return new BigDecimal("0.082");
    }

    if (days >= 21 && days <= 30) {
        return new BigDecimal("0.069");
    }

    if (days >= 31 && days <= 40) {
        return new BigDecimal("0.047");
    }

    return new BigDecimal("0.017");
}
}
