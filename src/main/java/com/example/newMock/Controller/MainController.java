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
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();
    public long startTime = 0L;
    BigDecimal maxLimit, balance;
    String currency;

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )

    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        return getObject(requestDTO);
    }

    @GetMapping(
            value = "/info/getBalances",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object getBalances(@RequestBody RequestDTO requestDTO) {
        return getObject(requestDTO);
    }

    private Object getObject(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            switch (clientId.charAt(0)) {
                case '8' -> {
                    currency = "US";
                    maxLimit = new BigDecimal("2000.00");
                    balance = BigDecimal.valueOf(Math.random() * 2000.00);
                }
                case '9' -> {
                    currency = "EU";
                    maxLimit = new BigDecimal("1000.00");
                    balance = BigDecimal.valueOf(Math.random() * 1000.00);
                }
                default -> {
                    currency = "RUB";
                    maxLimit = new BigDecimal("10000.00");
                    balance = BigDecimal.valueOf(Math.random() * 10000.00);
                }
            }
            balance = balance.setScale(2, RoundingMode.DOWN);
            ResponseDTO responseDTO = new ResponseDTO();
            setData(responseDTO, requestDTO);
            log.error("***** Запрос *****" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("***** Ответ *****" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
            doPacing();
            return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private void doPacing() throws InterruptedException {
        long pacing = ThreadLocalRandom.current().nextLong(100, 500);
        long endTime = System.currentTimeMillis();
        if (endTime - startTime < pacing) {
            Thread.sleep(pacing - (endTime - startTime));
        }
    }

    private void setData(ResponseDTO responseDTO, RequestDTO requestDTO) {
        responseDTO.setRqUID(requestDTO.getRqUID());
        responseDTO.setClientId(requestDTO.getClientId());
        responseDTO.setAccount(requestDTO.getAccount());
        responseDTO.setCurrency(currency);
        responseDTO.setBalance(balance);
        responseDTO.setMaxLimit(maxLimit);
    }
}