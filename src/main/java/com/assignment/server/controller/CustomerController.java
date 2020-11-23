package com.assignment.server.controller;

import com.assignment.server.datatransferobject.CustomerDTO;
import com.assignment.server.datatransferobject.TokenDTO;
import com.assignment.server.service.CustomerService;
import com.assignment.server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Customer controller
 */

@RestController
@RequestMapping(path = "/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    private TokenService tokenService;

    @Autowired
    public CustomerController(CustomerService customerService, TokenService tokenService) {
        this.customerService = customerService;
        this.tokenService = tokenService;
    }

    /**
     * retrieves a customer from the memory and returns it into response with the relevant HttpStatus code
     *
     * @param token      "my-token" should be passed in this variable
     * @param customerId the id of customer which should be searched
     * @return the customer corresponding to the customer id input wrapped in ResponseEntity in the body and
     * with the relevant HTTPStatus code
     */
    @GetMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDTO> getTransaction(@RequestHeader(name = "my-token", required = false) String token,
                                                      @PathVariable(name = "customerId") Long customerId) {
        if (token != null) {
            TokenDTO tokenDTO = tokenService.find(token);
            if (!tokenDTO.getToken().trim().equals("")) {
                if (customerId != null) {
                    CustomerDTO customerDTO = customerService.findById(customerId);
                    if (customerDTO.getCustomerId() != null) {
                        return ResponseEntity.ok(customerDTO);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomerDTO());
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerDTO());
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerDTO());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerDTO());
        }
    }
}
