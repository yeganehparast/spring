package me.leantech.server.controller;

import lombok.extern.java.Log;
import me.leantech.server.datatransferobject.TokenDTO;
import me.leantech.server.datatransferobject.TransactionDTO;
import me.leantech.server.service.TokenService;
import me.leantech.server.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Transactions Controller
 */
@RestController
@RequestMapping("/v1/transactions")
@Log
public class TransactionController {


    private TransactionService transactionService;

    private TokenService tokenService;

    public TransactionController(TransactionService transactionService, TokenService tokenService) {
        this.transactionService = transactionService;
        this.tokenService = tokenService;
    }

    @GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionDTO>> getTransactions(@RequestHeader(name = "lean-token", required = false) String token) {
        if (token != null) {
            TokenDTO tokenDTO = tokenService.find(token);
            if (!tokenDTO.getToken().trim().equals("")) {
                List<TransactionDTO> all = transactionService.getAll();
                return ResponseEntity.ok(all);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.EMPTY_LIST);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.EMPTY_LIST);
        }
    }

    /**
     * retrieves a transaction from the memory and returns it into response with the relevant HttpStatus code
     *
     * @param token         token "lean-token" should be passed in this variable
     * @param transactionId transactionId the id of transaction which should be searched
     * @return the transaction corresponding to the transaction id input wrapped in ResponseEntity in the body and
     * with the relevant HTTPStatus code
     */
    @GetMapping(value = "/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransaction(@RequestHeader(name = "lean-token", required = false) String token,
                                                         @PathVariable(name = "transactionId") Long transactionId) {
        if (token != null) {
            TokenDTO tokenDTO = tokenService.find(token);
            if (!tokenDTO.getToken().trim().equals("")) {
                if (transactionId != null) {
                    TransactionDTO transactionDTO = transactionService.findById(transactionId);
                    if (transactionDTO.getTransactionId() != null) {
                        return ResponseEntity.ok(transactionDTO);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TransactionDTO());
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionDTO());
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionDTO());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionDTO());
        }
    }

    /**
     * retrieves a list of transactions from the memory which their account id is given
     * and returns it into response with the relevant HttpStatus code
     *
     * @param token     token "lean-token" should be passed in this variable
     * @param accountId the id of account which should be searched
     * @return the transaction list of a specific account wrapped in ResponseEntity in the body and
     * with the relevant HTTPStatus code
     */
    @GetMapping(value = "/accountTransactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionList(@RequestHeader(name = "lean-token", required = false) String token,
                                                                   @RequestParam(name = "accountId") Long accountId) {
        if (token != null) {
            TokenDTO tokenDTO = tokenService.find(token);
            if (!tokenDTO.getToken().trim().equals("")) {
                if (accountId != null) {
                    List<TransactionDTO> transList = transactionService.findByAccountId(accountId);
                    if (!transList.isEmpty()) {
                        return ResponseEntity.ok(transList);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }
    }
}
