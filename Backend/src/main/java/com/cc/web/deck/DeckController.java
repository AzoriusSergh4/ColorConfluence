package com.cc.web.deck;

import com.cc.security.user.UserComponent;
import com.cc.web.entity.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://azoriussergh4.github.io"})
@RestController
@RequestMapping("/api/deck")
public class DeckController {


    private final DeckService deckService;

    private final UserComponent userComponent;

    @Autowired
    public DeckController(DeckService deckService, UserComponent userComponent) {
        this.deckService = deckService;
        this.userComponent = userComponent;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> saveDeck(@RequestBody DeckForm deckForm) {
        try{
            Deck deck = deckService.saveDeck(userComponent.getLoggedUser(), deckForm);
            return new ResponseEntity<>(deck.getId(),HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/decks/{id}")
    public List<Deck> getUserDecks(@PathVariable long id) {
        return deckService.findUserDecks(id);
    }

    @GetMapping("/{id}")
    public Deck getDeckById(@PathVariable long id) {
        return deckService.getDeckById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeck(@PathVariable long id) {
        Deck deck = this.deckService.getDeckById(id);
        if(deck == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(!deck.getUser().equals(userComponent.getLoggedUser())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        this.deckService.deleteDeck(deck);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
