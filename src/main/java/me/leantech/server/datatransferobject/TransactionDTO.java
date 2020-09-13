package me.leantech.server.datatransferobject;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A DTO class to hold transaction records
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionDTO {

    private Long transactionId;
    private Long accountId;
    private String transactionType;
    private String description;
    private BigDecimal amount;
    private String currencyCode;
    private LocalDateTime timestamp;
}
