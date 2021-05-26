package com.cc.web.specifications;

import com.cc.web.entity.Deck;
import com.cc.web.entity.Format;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public final class DeckSpecification extends CommonSpecification {

    public static Specification<Deck> nameContains(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), contains(name));
    }

    public static Specification<Deck> colorContains(String colorCriteria) {
        var defaultColors = new String[]{"W", "B", "U", "R", "G", "C"};
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String c : defaultColors) {
                if (colorCriteria.contains(c)) {
                    predicates.add(builder.like(root.get("colors"), contains(c)));
                } else {
                    predicates.add(builder.notLike(root.get("colors"), contains(c)));
                }
            }
            return builder.and(predicateListToArray(predicates));
        };
    }

    public static Specification<Deck> formatEquals(String format) {
        return (root, query, builder) -> {
            Join<Deck, Format> join = root.join("format");
            return builder.equal(join.get("name"), format);
        };
    }
}
