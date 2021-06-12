package com.cc.web.deck;

import com.cc.security.user.User;
import com.cc.web.card.CardRepository;
import com.cc.web.entity.*;
import com.cc.web.entity.payloads.DeckForm;
import com.cc.web.entity.payloads.FolderForm;
import com.cc.web.entity.projection.DeckFolderProjection;
import com.cc.web.entity.projection.DeckProjection;
import com.cc.web.specifications.DeckSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class DeckService {

    private final DeckRepository deckRepository;

    private final CardRepository cardRepository;

    private final DeckFolderRepository deckFolderRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository, CardRepository cardRepository, DeckFolderRepository deckFolderRepository) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
        this.deckFolderRepository = deckFolderRepository;
    }

    /**
     * Deletes the deck form the database
     *
     * @param deck The deck to delete
     */
    public void deleteDeck(Deck deck) {
        this.deckRepository.delete(deck);
    }


    /**
     * Retrieves the deck identified by the id
     *
     * @param id the id of the deck
     * @return the deck data, or null if it is not found
     */
    public Deck getDeckById(long id) {
        Optional<Deck> opt = deckRepository.findById(id);
        return opt.orElse(null);
    }

    /**
     * Retrieves the decks filtered by the specified criteria
     *
     * @param criteria the criteria to filter by
     * @return a page with the decks
     */
    public Page<DeckProjection> getBasicDecksByCriteria(Map<String, String> criteria) {
        Specification<Deck> specification = Specification
                .where(criteria.get("name") == null ? null : DeckSpecification.nameContains(criteria.get("name")))
                .and(criteria.get("colors") == null ? null : DeckSpecification.colorContains(criteria.get("colors")))
                .and(criteria.get("format") == null ? null : DeckSpecification.formatEquals(criteria.get("format")));
        return deckRepository.findAll(specification, DeckProjection.class, PageRequest.of(Integer.parseInt(criteria.get("page")), 20, Sort.by(Sort.Direction.DESC, "id")));
    }

    /**
     * Retrieves all decks the given user has
     *
     * @param userId the user id
     * @return the lsit of decks
     */
    public DeckFolderProjection findUserDecks(long userId) {
        Optional<DeckFolderProjection> opt = deckFolderRepository.findByRootIsTrueAndUser_Id(userId);
        return opt.orElse(null);
    }

    /**
     * Retrieve the folder identified by the given id
     *
     * @param folderId the id of the folder
     * @return the folder
     */
    public DeckFolder getFolder(long folderId) {
        Optional<DeckFolder> opt = deckFolderRepository.findById(folderId);
        return opt.orElse(null);
    }

    /**
     * Stores the new folder
     *
     * @param form   the info of the folder
     * @param parent the parent folder
     * @return the parent folder with the updated data
     */
    public DeckFolder saveFolder(FolderForm form, DeckFolder parent) {
        var folder = new DeckFolder();
        folder.setUser(parent.getUser());
        folder.setName(form.getName());
        folder.setRoot(false);
        folder.setParent(parent);
        return deckFolderRepository.save(folder);
    }

    /**
     * Deletes the given folder
     *
     * @param folder the folder to delete
     */
    public void deleteFolder(DeckFolder folder) {
        this.deckFolderRepository.delete(folder);
    }

    /**
     * Moves a folder from a folder to another
     *
     * @param folder the folder to move
     * @param to     the destination folder
     */
    public void moveFolder(DeckFolder folder, DeckFolder to) {
        folder.setParent(to);
        this.deckFolderRepository.save(folder);
    }

    /**
     * Moves a deck from a folder to another
     *
     * @param deck the deck to move
     * @param to   the destination folder
     */
    public void moveDeck(Deck deck, DeckFolder to) {
        deck.setFolder(to);
        this.deckRepository.save(deck);
    }

    /**
     * Stores the deck in the database
     *
     * @param user     the creator of the deck
     * @param deckForm the form with the deck data
     * @throws IllegalArgumentException if any card id is wrong
     */
    public Deck saveDeck(User user, DeckForm deckForm) throws IllegalArgumentException {
        Deck deck;
        //Check if new or existing deck
        if (deckForm.getId() > 0) {
            Optional<Deck> opt = deckRepository.findById(deckForm.getId());
            if (opt.isPresent() && opt.get().getUser().getEmail().equals(user.getEmail())) {
                deck = opt.get();
            } else {
                throw new IllegalArgumentException("Deck with wrong id or wrong user");
            }
        } else {
            deck = new Deck();
        }

        //Basic data
        deck.setName(deckForm.getName());
        deck.setComments(deckForm.getComments());
        deck.setFormat(deckForm.getFormat());
        deck.setColors("");
        deck.setUser(user);

        //Set Folder
        if (deck.getFolder() == null) {
            if (deckForm.getFolderId() > 0) {
                deck.setFolder(deckFolderRepository.findById(deckForm.getFolderId()).get());
            } else {
                var root = deckFolderRepository.getByRootIsTrueAndUser_Id(user.getId());
                deck.setFolder(root);
            }
        }


        //card additions
        List<DeckCard> commanders = new ArrayList<>();
        checkCard(commanders, deckForm.getCommanders(), deck);
        deck.setCommander(commanders);

        List<DeckCard> main = new ArrayList<>();
        checkCard(main, deckForm.getMain(), deck);
        deck.setMain(main);

        List<DeckCard> sideboard = new ArrayList<>();
        checkCard(sideboard, deckForm.getSideboard(), deck);
        deck.setSideboard(sideboard);

        //Check if colorless deck
        if (deck.getColors().equals("")) deck.setColors("C");

        //Check if the deck structure is correct or is a draft
        if (!deck.getCommander().isEmpty()) {
            List<DeckCard> cards = new ArrayList<>(deck.getCommander());
            cards.addAll(deck.getMain());
            if (getTotalQuantity(cards) != 100) {
                deck.setDraft(true);
            }
        } else {
            List<DeckCard> cards = new ArrayList<>(deck.getMain());
            if (getTotalQuantity(cards) < 60) {
                deck.setDraft(true);
            }
        }

        return deckRepository.save(deck);
    }

    private int getTotalQuantity(List<DeckCard> cards) {
        var quantity = 0;
        for (DeckCard card : cards) {
            quantity += card.getQuantity();
        }
        return quantity;
    }

    private void checkCard(List<DeckCard> deckCards, List<DeckForm.DeckCardForm> deckCardForms, Deck deck) {
        for (DeckForm.DeckCardForm c : deckCardForms) {
            CardCC card = cardRepository.findById(c.getId());
            if (card == null) {
                throw new IllegalArgumentException("Card with id " + c.getId() + " not found");
            }
            if (deck.getFormat() != null && !checkFormat(card, deck.getFormat().getName())) {
                throw new IllegalArgumentException("Deck is not legal for the format " + deck.getFormat().getName() + " because of card '" + card.getName() + "'");
            }
            deckCards.add(new DeckCard(card, c.getQuantity()));
            updateColorIdentity(deck, card);
        }
    }

    private boolean checkFormat(CardCC card, String format) {
        for (CardLegality l : card.getLegalities()) {
            if (l.getFormat().equals(format) && l.getLegality().equals("Legal")) return true;
        }
        return false;
    }

    private void updateColorIdentity(Deck deck, CardCC cardCC) {
        var defaultColors = new String[]{"W", "B", "U", "R", "G"};
        for (String color : defaultColors) {
            if (cardCC.getManaCost() != null && cardCC.getManaCost().contains(color) && !deck.getColors().contains(color)) {
                deck.setColors(deck.getColors() + "{" + color + "}");
            }
        }
    }
}
