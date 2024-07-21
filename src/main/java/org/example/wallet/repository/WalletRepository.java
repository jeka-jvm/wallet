package org.example.wallet.repository;

import org.example.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface WalletRepository extends JpaRepository<Wallet, UUID> {

}