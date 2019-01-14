package org.example.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class QuoteStreamListener {

    @Autowired
    private QuoteRepository quoteRepository;


    @StreamListener(Sink.INPUT)
    public void createQuoteFromMessage(Quote quote) {
        quoteRepository.save(quote);
    }
}
