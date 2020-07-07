package com.shop.advance.academy.yordan.petrov.git.shop.rest.controllers;

import com.shop.advance.academy.yordan.petrov.git.shop.domain.models.TransactionServiceModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.models.TransactionServiceViewModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/transactions")
@Slf4j
public class CardTransactionController {

    private final TransactionService transactionService;

    @Autowired
    public CardTransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping()
    public ResponseEntity<TransactionServiceViewModel> createTransaction(@RequestBody TransactionServiceModel transactionServiceModel) {

        TransactionServiceViewModel transactionServiceViewModel = transactionService.createTransaction(transactionServiceModel);

        URI location = MvcUriComponentsBuilder.fromMethodName(CardTransactionController.class, "createTransaction", TransactionServiceViewModel.class)
                .pathSegment("{id}").buildAndExpand(transactionServiceViewModel.getId()).toUri();

        log.info("Transaction created: {}", location);

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionServiceViewModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<TransactionServiceViewModel> updateTransaction(@PathVariable("id") Long id, @RequestBody TransactionServiceModel transactionServiceModel) {

        TransactionServiceViewModel transactionServiceViewModel = transactionService.updateTransaction(transactionServiceModel);

        log.info("Transaction updated: {}", transactionServiceViewModel);

        return ResponseEntity.status(HttpStatus.OK).body(transactionServiceViewModel);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TransactionServiceViewModel> partialUpdateTransaction(@PathVariable("id") Long id, @RequestBody TransactionServiceModel transactionServiceModel) {

        TransactionServiceViewModel transactionServiceViewModel = transactionService.updateTransaction(transactionServiceModel);

        log.info("Transaction updated: {} , ", transactionServiceViewModel);

        return ResponseEntity.status(HttpStatus.OK).body(transactionServiceViewModel);
    }


    @GetMapping("{id}")
    public ResponseEntity<TransactionServiceViewModel> getTransaction(@PathVariable("id") final Long id) {

        TransactionServiceViewModel transactionServiceViewModel = transactionService.getTransactionById(id);

        log.info("Transaction Found: {} ", transactionServiceViewModel);

        return ResponseEntity.status(HttpStatus.FOUND).body(transactionServiceViewModel);

    }

    @GetMapping()
    public ResponseEntity<List<TransactionServiceViewModel>> getTransactions() {

        List<TransactionServiceViewModel> transactionServiceViewModel = transactionService.getAllTransactions();

        log.info("Transactions Found: {} ", transactionServiceViewModel);

        return ResponseEntity.status(HttpStatus.FOUND).body(transactionServiceViewModel);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionServiceViewModel> deleteTransaction(@PathVariable("id") Long id) {

        TransactionServiceViewModel transactionServiceViewModel = transactionService.deleteTransactionById(id);

        log.info("Transaction deleted: {} ", transactionServiceViewModel);

        return ResponseEntity.status(HttpStatus.OK).body(transactionServiceViewModel);

    }


}