package org.example.wallet.service;


import org.example.wallet.dto.WalletOperationRequest;
import org.example.wallet.entity.Wallet;

import java.util.List;


public interface WalletService {

    void createWallet();

    Wallet getWalletBalance(String walletId);

    List<Wallet> getWallets();

    void executeOperation(WalletOperationRequest operationRequest);
}
