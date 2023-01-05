package com.axia.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class BtcWallet {
    @Id
    private long mb_idx;
    private String address;
    private String publicKey;
    private String privateKey;
    private BigDecimal balance;
}
