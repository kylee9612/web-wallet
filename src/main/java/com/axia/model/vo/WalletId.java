package com.axia.model.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class WalletId implements Serializable {
    @Column(name = "address")
    private String address;
    @Column(name = "memo")
    private String memo;
}
