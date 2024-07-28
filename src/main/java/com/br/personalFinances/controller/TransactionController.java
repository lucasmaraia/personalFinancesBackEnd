package com.br.personalFinances.controller;

import com.br.personalFinances.entity.Transaction;
import com.br.personalFinances.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> save(@RequestBody Transaction transaction){
        return ResponseEntity.ok(transactionService.save(transaction));
    }

    @GetMapping
    public ResponseEntity<?> find(){
        return ResponseEntity.ok(transactionService.find());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(transactionService.delete(id));
    }

    @GetMapping("/excel")
    public ResponseEntity<?> getExcel() throws IOException {
        return transactionService.downloadTransactionExcel();
    }


}
