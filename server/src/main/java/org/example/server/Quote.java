package org.example.server;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "quote")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String quote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote1 = (Quote) o;
        return Objects.equals(id, quote1.id) &&
                Objects.equals(quote, quote1.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quote);
    }
}
