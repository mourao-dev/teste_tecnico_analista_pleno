package teste_tecnico_analista_pleno.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import teste_tecnico_analista_pleno.domain.Transfer;
import teste_tecnico_analista_pleno.dto.TransferRequestDto;
import teste_tecnico_analista_pleno.dto.TransferResponseDto;
import teste_tecnico_analista_pleno.handler.exceptions.AccountNotFound;
import teste_tecnico_analista_pleno.mapper.TransferMapper;
import teste_tecnico_analista_pleno.repository.TransferRepository;
import teste_tecnico_analista_pleno.service.fee.FeeCalculator;

@Service
public class TransferService implements TransferServiceInterface {
    private final TransferRepository repository;
    private final TransferMapper mapper;
    private final FeeCalculator feeCalculator = new FeeCalculator();

    public TransferService(TransferRepository repository, TransferMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TransferResponseDto create(TransferRequestDto request) {

        validateAccount(request.getOriginAccount());
        validateAccount(request.getDestinationAccount());

        Transfer transfer = new Transfer();

        transfer.setOriginAccount(request.getOriginAccount());
        transfer.setDestinationAccount(request.getDestinationAccount());
        transfer.setAmount(request.getAmount());
        transfer.setTransferDate(request.getTransferDate());
        transfer.setAppointmentDate(LocalDate.now());

        transfer.setFee(calculateFee(transfer));

        Transfer saved = repository.save(transfer);

        return mapper.toResponse(saved);
    }

   @Override
public List<TransferResponseDto> getByAccount(String account) {

    List<Transfer> transfers = repository.findByOriginAccount(account);

if (transfers.isEmpty()) {
    throw new AccountNotFound(
        "Nenhuma transferência encontrada para a conta " + account);
}

    return transfers.stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
}

    public BigDecimal calculateFee(Transfer transfer) {
        long days = ChronoUnit.DAYS.between(
                transfer.getAppointmentDate(),
                transfer.getTransferDate());

        return feeCalculator.calculate(days, transfer.getAmount());
    }

    private void validateAccount(String account) {

        if (account == null || account.length() != 10) {
            throw new IllegalArgumentException(
                    "A conta deve possuir 10 caracteres");
        }
    }
}
