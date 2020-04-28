package com.learn.cloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author xu.rb
 * @since 2020-04-28 17:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;

    private Long userId;

    private BigDecimal total;

    private BigDecimal used;

    private BigDecimal  residue;
}
