package com.xrp.model.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class XrpAccount {

    @Id
    private long mb_idx;

    private int destination;

    private BigDecimal balance;

}
