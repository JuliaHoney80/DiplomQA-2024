package data;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Card {
    private String cardNumber;
    private String month;
    private String year;
    private String owner;
    private String cvc;


}
