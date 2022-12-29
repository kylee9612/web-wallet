package com.xrp.model.vo;

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

    @PostConstruct
    private void init(){
        destination = (int) (Math.random()*10000000);
    }
}
