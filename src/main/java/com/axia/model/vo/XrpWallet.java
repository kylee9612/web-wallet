package com.axia.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.xrpl.xrpl4j.wallet.Wallet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "xrp_wallet")
@NoArgsConstructor
@AllArgsConstructor
public class XrpWallet{
    @Id
    private String address;

    @Column
    private String publicKey;

    @Column
    private String privateKey;

    @Column
    private BigDecimal balance;

    public XrpWallet(Wallet wallet){
        this.address = wallet.classicAddress().toString();
        this.publicKey = wallet.publicKey();
        this.privateKey = wallet.privateKey().get();
    }
}
