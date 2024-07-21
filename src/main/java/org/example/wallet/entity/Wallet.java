package org.example.wallet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private BigDecimal balance;

    public Wallet(BigDecimal balance) {
        this.balance = balance;
    }

}
