package com.xrp.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.PostConstruct;
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
    private long mb_idx;

    @Column
    private String address;
    @Column
    private long destination;

    @Column
    private BigDecimal balance;
}
