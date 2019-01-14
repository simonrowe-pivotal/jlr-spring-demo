package com.example.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
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
    @HystrixCommand(fallbackMethod = "quoteFallback")
    public List<Quote> quotes() throws Exception {
        return restTemplate.getForObject("//server/quotes", QuoteList.class);
    }

    public List<Quote> quoteFallback() {
        Quote sample = new Quote();
        sample.setQuote("When our downstream service has issues - here is a sample quote for you = Chiz!");
        return Arrays.asList(sample);
    }

}

class QuoteList extends ArrayList<Quote> {}
