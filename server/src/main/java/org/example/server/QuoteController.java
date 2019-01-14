package org.example.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    @Value("${my.property:Property Not Set}")
    private String myProperty;

    @GetMapping("/hello")
    public String hello() {
        return "Hello JLR!!!";
    }

    @GetMapping("/myProperty")
    public String myProperty() {
        return myProperty;
    }

    @PostMapping("/quotes")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuoteFromHttpCall(@RequestBody Quote quote) {
        quoteRepository.save(quote);
    }

    @GetMapping("/quotes")
    public Iterable<Quote> getQuotes() {
        return quoteRepository.findAll();
    }

}
