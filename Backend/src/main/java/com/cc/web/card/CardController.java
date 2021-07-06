package com.cc.web.card;

import com.cc.web.entity.CardPopularity;
import com.cc.web.entity.CardTranslation;
import com.cc.web.entity.projection.CardTranslationProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(origins = {"http://localhost:4200", "https://azoriussergh4.github.io"})
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/full")
    public Page<CardTranslation> getFullCardByName(@RequestParam Map<String, String> criteria) {
        return cardService.getFullCardsByCriteria(criteria);
    }

    @GetMapping("/size")
    public long countByName(@RequestParam Map<String, String> criteria) {
        return cardService.countBasicCardsByCriteria(criteria);
    }

    @GetMapping("/basic")
    public Page<CardTranslationProjection> getBasicCardsByCriteria(@RequestParam Map<String, String> criteria) {
        return cardService.getBasicCardsByCriteria(criteria);
    }

    @GetMapping("/{id}")
    public CardTranslation getCardById(@PathVariable long id) {
        return cardService.getCardById(id);
    }

    @GetMapping("/popularity")
    public Page<CardPopularity> getCardPopularities(@RequestParam int page){
        return cardService.getCardPopularities(page);
    }
}
