package com.cc.web.deck;

import com.cc.security.user.User;
import com.cc.web.card.CardRepository;
import com.cc.web.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;


    /**
     * Retrieves the deck identified by the id
     * @param id the id of the deck
     * @return the deck data, or null if it is not found
     */
    public Deck getDeckById(long id) {
        return deckRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves the deck identified by the name
     * @param name the id of the deck
     * @return the deck data, or null if it is not found
     */
    public Deck getDeckByName(String name) {
        return deckRepository.getByName(name).orElse(null);
    }


    /**
     * Stores the deck in the database
     * @param user the creator of the deck
     * @param deckForm the form with the deck data
     * @throws IllegalArgumentException if any card id is wrong
     */
    public void saveDeck(User user, DeckForm deckForm) throws IllegalArgumentException {
        Deck deck = new Deck();

        deck.setName(deckForm.getName());
        deck.setComments(deckForm.getComments());
        deck.setFormats(deckForm.getFormats());
        deck.setColors("");
        deck.setUser(user);

        //card additions
        List<DeckCard> commanders = new ArrayList<>();
        checkCard(commanders,deckForm.getCommanders(),deck);
        deck.setCommander(commanders);

        List<DeckCard> main = new ArrayList<>();
        checkCard(main,deckForm.getMain(),deck);
        deck.setMain(main);

        List<DeckCard> sideboard = new ArrayList<>();
        checkCard(sideboard,deckForm.getSideboard(),deck);
        deck.setSideboard(sideboard);

        //Check if colorless deck
        if (deck.getColors().equals("")) deck.setColors("C");

        //Check if the deck structure is correct or is a draft
        if(deck.getCommander().size() > 0) {
            List<DeckCard> cards = new ArrayList<>(deck.getCommander());
            cards.addAll(deck.getMain());
            if (getTotalQuantity(cards) != 100) {
                deck.setDraft(true);
            }
        }else {
            List<DeckCard> cards = new ArrayList<>(deck.getMain());
            if (getTotalQuantity(cards) < 60) {
                deck.setDraft(true);
            }
        }

        deckRepository.save(deck);
    }

    private int getTotalQuantity(List<DeckCard> cards) {
        int quantity = 0;
        for(DeckCard card : cards) {
            quantity += card.getQuantity();
        }
        return quantity;
    }

    private void checkCard(List<DeckCard> deckCards, List<DeckForm.DeckCardForm> deckCardForms, Deck deck) {
        for(DeckForm.DeckCardForm c : deckCardForms) {
            CardCC card = cardRepository.findById(c.getId());
            if(card == null) {
                throw new IllegalArgumentException("Card with id " + c.getId() + " not found");
            }
            for(Format f : deck.getFormats()) {
                if (!checkFormat(card, f.getName())) {
                    throw new IllegalArgumentException("Deck is not legal for the format " + f.getName() + " because of card '" + card.getName() + "'");
                }
            }
            deckCards.add(new DeckCard(card, c.getQuantity()));
            updateColorIdentity(deck,card);
        }
    }

    private boolean checkFormat(CardCC card, String format) {
        for(CardLegality l : card.getLegalities()) {
            if(l.getFormat().equals(format) && l.getLegality().equals("Legal")) return true;
        }
        return false;
    }

    private void updateColorIdentity(Deck deck, CardCC cardCC) {
        String[] defaultColors = {"W", "B", "U" ,"R", "G"};
        for(String color : defaultColors) {
            if(cardCC.getManaCost() != null && cardCC.getManaCost().contains(color) && !deck.getColors().contains(color)){
                deck.setColors(deck.getColors() + color);
            }
        }
    }
}
