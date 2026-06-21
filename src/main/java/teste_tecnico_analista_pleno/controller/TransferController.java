package teste_tecnico_analista_pleno.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teste_tecnico_analista_pleno.dto.*;
import teste_tecnico_analista_pleno.service.TransferService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/transfers")
@CrossOrigin(origins = "http://localhost:5173")
public class TransferController {

    private final TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransferResponseDto> create(
            @RequestBody @Valid TransferRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/{account}")
    public ResponseEntity<List<TransferResponseDto>> getByAccount(
            @PathVariable String account) {

        return ResponseEntity.ok(service.getByAccount(account));
    }
}
