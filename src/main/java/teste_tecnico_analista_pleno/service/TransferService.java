package teste_tecnico_analista_pleno.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import teste_tecnico_analista_pleno.domain.Transfer;
import teste_tecnico_analista_pleno.dto.TransferRequestDto;
import teste_tecnico_analista_pleno.dto.TransferResponseDto;
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
}
