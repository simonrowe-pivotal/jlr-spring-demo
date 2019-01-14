package com.example.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Source source;

    @PostMapping("/quoteHttp")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuoteViaRest(@RequestBody Quote quote) {
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("//server/quotes", quote, Void.class);
        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Not sure what has happened here!");
        }
    }

    @PostMapping("/quoteMessaging")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuoteViaMessaging(@RequestBody Quote quote) {
        source.output().send(MessageBuilder.withPayload(quote).build());
    }

    @GetMapping("/quotes")
    public List<Quote> quotes() throws Exception {
        return restTemplate.getForObject("//server/quotes", QuoteList.class);
    }

}

class QuoteList extends ArrayList<Quote> {}
