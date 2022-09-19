package net.armanit.springsecuritydemo.controller;

import net.armanit.springsecuritydemo.model.Cards;
import net.armanit.springsecuritydemo.model.Customer;
import net.armanit.springsecuritydemo.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardController {
    @Autowired
    private CardsRepository cardsRepository;

    @PostMapping("/myCards")
    public List<Cards> getCardDetails(@RequestBody Customer customer) {
        List<Cards> cards = cardsRepository.findByCustomerId(customer.getId());
        if (cards != null ) {
            return cards;
        }else {
            return null;
        }
    }
}
