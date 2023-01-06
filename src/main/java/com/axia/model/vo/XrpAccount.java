package com.axia.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "xrp_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class XrpAccount {

    @Id
    private int mb_idx;

    @Column
    private String address;
    @Column
    private int destination;

    @Column
    private BigDecimal balance;
}
