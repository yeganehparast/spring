package com.assignment.server.datatransferobject;

import lombok.*;

import java.math.BigDecimal;

/**
 * A DTO class to hold account records
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountDTO {
    private Long accountId;
    private Long customerId;
    private String accountType;
    private Long accountNumber;
    private String ibanCode;
    private String accountStatus;
    private BigDecimal balance;
    private String currencyCode;
}
