package com.assignment.server.controller;

import com.assignment.server.datatransferobject.AccountDTO;
import com.assignment.server.datatransferobject.TokenDTO;
import com.assignment.server.service.AccountService;
import com.assignment.server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Accounts Controller
 */
@RestController
@RequestMapping(path = "/v1/accounts")
public class AccountController {

    private AccountService accountService;

    private TokenService tokenService;

    @Autowired
    public AccountController(AccountService accountService, TokenService tokenService) {
        this.accountService = accountService;
        this.tokenService = tokenService;
    }

    /**
     * retrieves an account from the memory and returns it into response with the relevant HttpStatus code
     *
     * @param token     "my-token" should be passed in this variable
     * @param accountId the id of account which should be searched
     * @return the account corresponding to the account id input wrapped in ResponseEntity in the body and
     * with the relevant HTTPStatus code
     */
    @GetMapping(value = "/{accountId}")
    public ResponseEntity<AccountDTO> getTransaction(@RequestHeader(name = "my-token", required = false) String token,
                                                     @PathVariable(name = "accountId") Long accountId) {
        if (token != null) {
            TokenDTO tokenDTO = tokenService.find(token);
            if (!tokenDTO.getToken().trim().equals("")) {
                if (accountId != null) {
                    AccountDTO accountDTO = accountService.findById(accountId);
                    if (accountDTO.getAccountId() != null) {
                        return ResponseEntity.ok(accountDTO);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AccountDTO());
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AccountDTO());
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AccountDTO());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AccountDTO());
        }
    }
}
