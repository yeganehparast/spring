package com.assignment.server.datatransferobject;

import lombok.*;

import java.time.LocalDate;

/**
 * A DTO class to hold Customer records
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerDTO {
    private Long customerId;
    private String name;
    private LocalDate dateOfBirth;
    private String nationality;
    private String emailAddress;
    private String address;

}
