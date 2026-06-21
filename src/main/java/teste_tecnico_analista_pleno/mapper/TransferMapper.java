package teste_tecnico_analista_pleno.mapper;

import org.springframework.stereotype.Component;

import teste_tecnico_analista_pleno.domain.Transfer;
import teste_tecnico_analista_pleno.dto.TransferResponseDto;

@Component
public class TransferMapper {
    public TransferResponseDto toResponse(Transfer transfer) {

        TransferResponseDto dto = new TransferResponseDto();

        dto.setId(transfer.getId());
        dto.setOriginAccount(transfer.getOriginAccount());
        dto.setDestinationAccount(transfer.getDestinationAccount());
        dto.setAmount(transfer.getAmount());
        dto.setFee(transfer.getFee());
        dto.setTransferDate(transfer.getTransferDate());

        return dto;
    }
}
