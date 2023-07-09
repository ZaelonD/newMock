package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();
    public long startTime = 0L;

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )


    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String currency, clientId = requestDTO.getClientId();
            BigDecimal maxLimit, balance;
            //String currency;
            if (clientId.charAt(0) == '8') {
                currency = "US";
                maxLimit = new BigDecimal("2000.00");
                balance = new BigDecimal(Math.round(Math.random() * 2000.00 ));
            } else if (clientId.charAt(0) == '9') {
                currency = "EU";
                maxLimit = new BigDecimal("1000.00");
                balance = new BigDecimal(Math.round(Math.random() * 1000.00));
            } else {
                currency = "RUB";
                maxLimit = new BigDecimal("10000.00");
                balance = new BigDecimal(Math.round(Math.random() * 10000.00));
            }
            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(requestDTO.getClientId());
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("***** Запрос *****" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("***** Ответ *****" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            long pacing = ThreadLocalRandom.current().nextLong(100, 500);
            long endTime = System.currentTimeMillis();
            if (endTime - startTime < pacing) {
                Thread.sleep(pacing - (endTime - startTime));
            }

            return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(
            value = "/info/getBalances",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object getBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String currency, clientId = requestDTO.getClientId();
            BigDecimal maxLimit, balance;
            //String currency;
            if (clientId.charAt(0) == '8') {
                currency = "US";
                maxLimit = new BigDecimal("2000.00");
                balance = new BigDecimal(Math.round(Math.random() * 2000.00));
            } else if (clientId.charAt(0) == '9') {
                currency = "EU";
                maxLimit = new BigDecimal("1000.00");
                balance = new BigDecimal(Math.round(Math.random() * 1000.00));
            } else {
                currency = "RUB";
                maxLimit = new BigDecimal("10000.00");
                balance = new BigDecimal(Math.round(Math.random() * 10000.00));
            }
            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(requestDTO.getClientId());
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("***** Запрос *****" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("***** Ответ *****" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}