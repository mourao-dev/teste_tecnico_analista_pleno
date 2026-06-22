package teste_tecnico_analista_pleno;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import teste_tecnico_analista_pleno.domain.Transfer;
import teste_tecnico_analista_pleno.dto.TransferRequestDto;
import teste_tecnico_analista_pleno.dto.TransferResponseDto;
import teste_tecnico_analista_pleno.handler.exceptions.FeeNotApplicableException;
import teste_tecnico_analista_pleno.mapper.TransferMapper;
import teste_tecnico_analista_pleno.repository.TransferRepository;
import teste_tecnico_analista_pleno.service.TransferService;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private TransferRepository repository;

    @Mock
    private TransferMapper mapper;

    @InjectMocks
    private TransferService service;

    private TransferRequestDto request;

    @BeforeEach
    void setup() {
        request = new TransferRequestDto();
        request.setOriginAccount("1234567890");
        request.setDestinationAccount("0987654321");
        request.setAmount(new BigDecimal("100.00"));
        request.setTransferDate(LocalDate.now().plusDays(5));
    }

    @Test
    void shouldCreateTransferSuccessfully() {
        Transfer saved = new Transfer();
        saved.setAmount(request.getAmount());

        TransferResponseDto response = new TransferResponseDto();

        when(repository.save(any(Transfer.class))).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        TransferResponseDto result = service.create(request);

        assertNotNull(result);
        verify(repository, times(1)).save(any(Transfer.class));
    }

    @Test
    void shouldThrowExceptionWhenOriginAccountIsInvalid() {
        request.setOriginAccount("123");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(request));

        assertEquals("A conta deve possuir 10 caracteres", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDestinationAccountIsInvalid() {
        request.setDestinationAccount("123");

        assertThrows(
                IllegalArgumentException.class,
                () -> service.create(request));
    }

    @Test
    void shouldReturnTransfersByAccount() {
        Transfer transfer = new Transfer();
        TransferResponseDto dto = new TransferResponseDto();

        when(repository.findByOriginAccount("1234567890"))
                .thenReturn(List.of(transfer));

        when(mapper.toResponse(transfer)).thenReturn(dto);

        List<TransferResponseDto> result = service.getByAccount("1234567890");

        assertEquals(1, result.size());
        verify(repository).findByOriginAccount("1234567890");
    }

    @Test
    void shouldApplyFeeForSameDayTransfer() {
        Transfer t = buildTransfer(0, new BigDecimal("100.00"));

        BigDecimal fee = service.calculateFee(t);

        assertEquals(new BigDecimal("2.50000"), fee);
    }

    @Test
    void shouldApplyZeroFeeForUpTo10Days() {
        Transfer t = buildTransfer(10, new BigDecimal("100.00"));

        BigDecimal fee = service.calculateFee(t);

        assertEquals(0, fee.compareTo(BigDecimal.ZERO));
    }

    @Test
    void shouldApplyFeeBetween11And20Days() {
        Transfer t = buildTransfer(15, new BigDecimal("100.00"));

        BigDecimal fee = service.calculateFee(t);

        assertEquals(new BigDecimal("8.20000"), fee);
    }

    @Test
    void shouldApplyFeeBetween21And30Days() {
        Transfer t = buildTransfer(25, new BigDecimal("100.00"));

        BigDecimal fee = service.calculateFee(t);

        assertEquals(new BigDecimal("6.90000"), fee);
    }

    @Test
    void shouldApplyFeeBetween31And40Days() {
        Transfer t = buildTransfer(35, new BigDecimal("100.00"));

        BigDecimal fee = service.calculateFee(t);

        assertEquals(new BigDecimal("4.70000"), fee);
    }

    @Test
    void shouldApplyFeeBetween41And50Days() {
        Transfer t = buildTransfer(45, new BigDecimal("100.00"));

        BigDecimal fee = service.calculateFee(t);

        assertEquals(new BigDecimal("1.70000"), fee);
    }

    @Test
    void shouldThrowExceptionWhenDaysGreaterThan50() {
        Transfer t = buildTransfer(60, new BigDecimal("100.00"));

        assertThrows(FeeNotApplicableException.class,
                () -> service.calculateFee(t));
    }

    @Test
    void shouldThrowExceptionWhenDaysNegative() {
        Transfer t = buildTransfer(-5, new BigDecimal("100.00"));

        assertThrows(FeeNotApplicableException.class,
                () -> service.calculateFee(t));
    }

    @Test
    void shouldThrowExceptionWhenAmountNegative() {
        Transfer t = buildTransfer(10, new BigDecimal("-100.00"));

        assertThrows(IllegalArgumentException.class,
                () -> service.calculateFee(t));
    }

    private Transfer buildTransfer(int days, BigDecimal amount) {
        Transfer t = new Transfer();
        t.setAmount(amount);
        t.setAppointmentDate(LocalDate.now());
        t.setTransferDate(LocalDate.now().plusDays(days));
        return t;
    }
}
