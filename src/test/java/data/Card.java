package data;

import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Value
public class Card {
    private String cardNumber;
    private String month;
    private String year;
    private String owner;
    private String cvc;


    public Card(String cardNumber, String month, String year, String owner, String cvc) {
        this.cardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.owner = owner;
        this.cvc = cvc;
    }



}

