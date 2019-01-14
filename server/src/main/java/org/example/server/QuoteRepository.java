package org.example.server;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuoteRepository extends CrudRepository<Quote, Integer> {
    //select * from quote where quote like '%blah%'
    List<Quote> findByQuoteContainsIgnoreCase(String quote);
}
