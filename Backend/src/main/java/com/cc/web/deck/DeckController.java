package com.cc.web.deck;

import com.cc.security.user.UserComponent;
import com.cc.web.entity.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://azoriussergh4.github.io"})
@RestController
@RequestMapping("/api/deck")
public class DeckController {

    @Autowired
    private DeckService deckService;

    @Autowired
    private UserComponent userComponent;

    @PostMapping("/create")
    public ResponseEntity<String> register(@RequestBody DeckForm deck) {
        try{
            deckService.saveDeck(userComponent.getLoggedUser(), deck);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Deck getDeckById(@PathVariable long id) {
        return deckService.getDeckById(id);
    }
}
