package teste_tecnico_analista_pleno.service;

import java.util.List;

import teste_tecnico_analista_pleno.dto.TransferRequestDto;
import teste_tecnico_analista_pleno.dto.TransferResponseDto;

public interface TransferServiceInterface {
  TransferResponseDto create(TransferRequestDto request);
    List<TransferResponseDto> getByAccount(String account);
}
