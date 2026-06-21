package teste_tecnico_analista_pleno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import teste_tecnico_analista_pleno.domain.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findByOriginAccount(String originAccount);
}
