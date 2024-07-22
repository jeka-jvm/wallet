package org.example.wallet.service.impl;

import org.example.wallet.dto.WalletOperationRequest;
import org.example.wallet.entity.Wallet;
import org.example.wallet.exception.InsufficientFundsException;
import org.example.wallet.exception.WalletNotFoundException;
import org.example.wallet.exception.InvalidOperationTypeException;
import org.example.wallet.model.OperationType;
import org.example.wallet.repository.WalletRepository;
import org.example.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void createWallet() {
        walletRepository.save(new Wallet(BigDecimal.ZERO));
    }

    @Override
    public Wallet getWalletBalance(String walletId) {
        return walletRepository.findById(UUID.fromString(walletId))
                               .orElseThrow(() -> new WalletNotFoundException("Кошелек не найден"));
    }

    @Override
    public List<Wallet> getWallets() {
        return walletRepository.findAll();
    }

    @Override
    @Transactional
    public void executeOperation(WalletOperationRequest operationRequest) {
        Wallet wallet = walletRepository.findByIdWithLock(operationRequest.getWalletId())
                                        .orElseThrow(() -> new WalletNotFoundException("Кошелек не найден"));

        if (operationRequest.getOperationType() == OperationType.DEPOSIT) {
            deposit(wallet, operationRequest.getAmount());
        } else if (operationRequest.getOperationType() == OperationType.WITHDRAW) {
            withdraw(wallet, operationRequest.getAmount());
        } else
            throw new InvalidOperationTypeException("Неверный тип операции");

        walletRepository.save(wallet);
    }

    private void deposit(Wallet wallet, BigDecimal amount) {
        wallet.setBalance(wallet.getBalance().add(amount));
    }

    private void withdraw(Wallet wallet, BigDecimal amount) {
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на кошельке");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
    }
}
