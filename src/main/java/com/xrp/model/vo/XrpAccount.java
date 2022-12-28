package com.xrp.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(schema = "test")
@Getter
@Setter
@NoArgsConstructor
public class XrpAccount {

    @Id
    private long mb_idx;

    @Column
    private String address;
    @Column
    private int destination;

    @Column
    private BigDecimal balance;

}
