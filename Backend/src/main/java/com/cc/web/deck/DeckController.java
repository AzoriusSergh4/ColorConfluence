package com.cc.web.deck;

import com.cc.security.user.UserComponent;
import com.cc.web.entity.Deck;
import com.cc.web.entity.DeckFolder;
import com.cc.web.entity.payloads.DeckForm;
import com.cc.web.entity.payloads.FolderForm;
import com.cc.web.entity.projection.DeckFolderProjection;
import com.cc.web.entity.projection.DeckProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;

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
        if (deckForm.getFolderId() > 0) {
            var f = deckService.getFolder(deckForm.getFolderId());
            if (invalidFolder(f)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            var deck = deckService.saveDeck(userComponent.getLoggedUser(), deckForm);
            return new ResponseEntity<>(deck.getId(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/decks")
    public Page<DeckProjection> getDecks(@RequestParam Map<String, String> criteria) {
        return deckService.getBasicDecksByCriteria(criteria);
    }

    @GetMapping("/decks/{id}")
    public DeckFolderProjection getUserDecks(@PathVariable long id) {
        return deckService.findUserDecks(id);
    }

    @PostMapping("/folder")
    public ResponseEntity<DeckFolder> newFolder(@RequestBody FolderForm folderForm) {
        var f = this.deckService.getFolder(folderForm.getParentId());
        if (invalidFolder(f)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(this.deckService.saveFolder(folderForm, f), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/folder")
    public ResponseEntity<Boolean> deleteFolder(@RequestBody FolderForm folderForm) {
        var f = deckService.getFolder(folderForm.getFolderId());
        if (invalidRootFolder(f)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (f.getDecks().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.deckService.deleteFolder(f);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/folder/move")
    public ResponseEntity<Boolean> moveFolder(@RequestBody FolderForm folderForm) {
        var folder = deckService.getFolder(folderForm.getFolderId());
        var to = deckService.getFolder(folderForm.getToId());
        if (invalidRootFolder(folder) || invalidFolder(to)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (!differentFolders(folderForm.getFolderId(), folderForm.getToId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.deckService.moveFolder(folder, to);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/move")
    public ResponseEntity<Boolean> moveDeck(@RequestBody FolderForm folderForm) {
        var deck = deckService.getDeckById(folderForm.getFromId());
        var to = deckService.getFolder(folderForm.getToId());
        if (invalidDeck(deck) || invalidFolder(to)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            this.deckService.moveDeck(deck, to);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public Deck getDeckById(@PathVariable long id) {
        return this.deckService.getDeckById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeck(@PathVariable long id) {
        var deck = this.deckService.getDeckById(id);
        if (deck == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!deck.getUser().getEmail().equals(userComponent.getLoggedUser().getEmail()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        this.deckService.deleteDeck(deck);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Check if the given folders are all different
     *
     * @param ids the ids of the folders
     * @return true if all folders are different, false in other case
     */
    private boolean differentFolders(long... ids) {
        HashSet<Long> set = new HashSet<>();
        for (long id : ids) {
            if (!set.add(id)) return false;
        }
        return true;
    }

    /**
     * Check if the deck is invalid
     *
     * @param d the deck
     * @return true if the deck is invalid, false in other case
     */
    private boolean invalidDeck(Deck d) {
        return d == null || !d.getUser().getEmail().equals(userComponent.getLoggedUser().getEmail());
    }

    /**
     * Check if the folder is invalid
     *
     * @param f the folder
     * @return true if the folder is invalid, false in other case
     */
    private boolean invalidFolder(DeckFolder f) {
        return f == null || !f.getUser().getEmail().equals(userComponent.getLoggedUser().getEmail());
    }


    /**
     * Check if the folder is invalid or if is the root folder
     *
     * @param f the folder
     * @return true if the folder is invalid or the root folder, false in other case
     */
    private boolean invalidRootFolder(DeckFolder f) {
        return invalidFolder(f) || f.isRoot();
    }
}
