package com.axia.model.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "w_xrp_wallet")
@Data
public class XrplWallet {
    @EmbeddedId
    private WalletId walletId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "public_key")
    private String pubKey;
    @Column(name = "balance")
    private BigDecimal balance;
}
