package xyz.marcaragones.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xyz.marcaragones.transactions.dto.TransactionDTO;
import xyz.marcaragones.transactions.service.transaction.TransactionService;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public void transactions(TransactionDTO body, HttpServletResponse response) {
        if (isValidTimestamp(body.getTimestamp())) {
            transactionService.commit(body);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    private boolean isValidTimestamp(Long timestamp) {
        return true;
    }
}
