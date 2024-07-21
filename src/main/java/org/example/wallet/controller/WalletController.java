package org.example.wallet.controller;

import jakarta.validation.Valid;
import org.example.wallet.dto.WalletOperationRequest;
import org.example.wallet.entity.Wallet;
import org.example.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallet/new")
    public ResponseEntity<String> createWallet() {
        walletService.createWallet();

        return ResponseEntity.ok("Новый кошелек успешно создан!");
    }

    @GetMapping("/wallets/{WALLET_ID}")
    public ResponseEntity<Wallet> getWalletBalance(@PathVariable("WALLET_ID") String WALLET_ID) {
        return ResponseEntity.ok(walletService.getWalletBalance(WALLET_ID));
    }

    @GetMapping("wallets")
    public ResponseEntity<List<Wallet>> getWallets() {
        return ResponseEntity.ok(walletService.getWallets());
    }

    @PostMapping("wallet")
    public ResponseEntity<String> executeOperation(@Valid @RequestBody WalletOperationRequest operationRequest) throws RuntimeException{
        walletService.executeOperation(operationRequest);

        return ResponseEntity.ok("Выполнение операции успешно завершено!");
    }

}
