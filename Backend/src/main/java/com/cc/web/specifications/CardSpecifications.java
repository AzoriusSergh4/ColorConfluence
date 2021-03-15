package com.cc.web.specifications;

import com.cc.web.entity.CardCC;
import com.cc.web.entity.CardLegality;
import com.cc.web.entity.CardSet;
import com.cc.web.entity.CardTranslation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CardSpecifications {

    //Translation Specifications

    public static Specification<CardTranslation> nameContains(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), contains(name));
    }

    public static Specification<CardTranslation> descriptionContains(String description){
        return (root, query, builder) -> builder.like(root.get("description"), contains(description));
    }

    public static Specification<CardTranslation> langEquals(String lang){
        return (root, query, builder) -> builder.equal(root.get("lang"), lang);
    }

    public static Specification<CardTranslation> loreContains(String lore){
        return (root, query, builder) -> builder.like(root.get("lore"), contains(lore));
    }

    public static Specification<CardTranslation> typeContains(String type){
        String[] types = (type.contains(",")) ? type.split(",") : new String[]{type};
        return (root, query, builder) -> {
            Join<CardTranslation, CardCC> join = root.join("card");
            List<Predicate> predicates = new ArrayList<>();
            for(String s : types){
                predicates.add(builder.like(join.get("cardType"), contains(s)));
            }
            return builder.and(predicateListToArray(predicates));
        };
    }
    public static Specification<CardTranslation> colorContains(String colorCriteria){
        String[] defaultColors = {"W", "B", "U" ,"R", "G", "C"};
        if(colorCriteria.startsWith("=")){
            String color = colorCriteria.replaceAll("=", "");
            return (root, query, builder) -> {
                Join<CardTranslation, CardCC> join = root.join("card");
                List<Predicate> predicates = new ArrayList<>();
                for(String c : defaultColors) {
                    if (color.contains(c)) {
                        predicates.add(builder.like(join.get("manaCost"), contains(c)));
                    } else {
                        predicates.add(builder.notLike(join.get("manaCost"), contains(c)));
                    }
                }
                return builder.and(predicateListToArray(predicates));
            };
        }else if(colorCriteria.startsWith("<=")){
            String color = colorCriteria.replaceAll("<=", "");
            return (root, query, builder) -> {
                Join<CardTranslation, CardCC> join = root.join("card");
                List<Predicate> predicates = new ArrayList<>();
                for(String c : defaultColors) {
                    if (color.contains(c)) {
                        predicates.add(builder.like(join.get("manaCost"), contains(c)));
                    }
                }
                return builder.and(predicateListToArray(predicates));
            };
        }else if (colorCriteria.startsWith("<")){
            String color = colorCriteria.replaceAll("<", "");
            return (root, query, builder) -> {
                Join<CardTranslation, CardCC> join = root.join("card");
                List<Predicate> predicates = new ArrayList<>();
                for(String c : defaultColors) {
                    if (color.contains(c)) {
                        predicates.add(builder.like(join.get("manaCost"), contains(c)));
                    }
                }
                return builder.or(predicateListToArray(predicates));
            };
        }else{
            return null;
        }
    }

    public static Specification<CardTranslation> manaCostContains(String manaCost){
        return (root, query, builder) -> {
            Join<CardTranslation, CardCC> join = root.join("card");
            return builder.like(join.get("manaCost"), contains(manaCost));
        };
    }

    public static Specification<CardTranslation> statComparatorContains(String content, String stat){

        if(content.startsWith(">=")){
            return (root, query, builder) -> {
                Join<CardTranslation, CardCC> join = root.join("card");
                return builder.greaterThanOrEqualTo(join.get(stat), content.replaceAll(">=", ""));
            };
        }else if(content.startsWith("<=")){
            return (root, query, builder) -> {
                Join<CardTranslation, CardCC> join = root.join("card");
                return builder.lessThanOrEqualTo(join.get(stat), content.replaceAll("<=", ""));
            };
        }else if(content.startsWith(">")){
            return (root, query, builder) -> {
                Join<CardTranslation, CardCC> join = root.join("card");
                return builder.greaterThan(join.get(stat), content.replaceAll(">", ""));
            };
        }else if(content.startsWith("<")){
            return (root, query, builder) -> {
                Join<CardTranslation, CardCC> join = root.join("card");
                return builder.lessThan(join.get(stat), content.replaceAll("<", ""));
            };
        }else{
            return (root, query, builder) -> {
                Join<CardTranslation, CardCC> join = root.join("card");
                return builder.equal(join.get(stat), content.replaceAll("=", ""));
            };
        }
    }

    public static Specification<CardTranslation> rarityContains(String rarity){
        return (root, query, builder) -> {
            Join<CardTranslation, CardCC> join = root.join("card");
            return builder.like(join.get("rarity"), contains(rarity));
        };
    }

    public static Specification<CardTranslation> cardSetContains(String cardSet){
        return (root, query, builder) -> {
            Join<CardSet, CardTranslation> join = root.join("cardSet");
            return builder.like(join.get("cardSet"), contains(cardSet));
        };
    }

    public static Specification<CardTranslation> legalityContains(String format, String legality){
        return (root, query, builder) -> {
            Join<CardTranslation, CardCC> join = root.join("card");
            Join<CardCC, CardLegality> joinL = join.join("legalities");
            return builder.and(builder.like(joinL.get("format"),contains(format)),builder.like(joinL.get("legality"), contains(legality)));
        };
    }

    private static Predicate[] predicateListToArray(List<Predicate> predicates){
        Predicate[] result = new Predicate[predicates.size()];
        for(int i = 0; i< predicates.size(); i++){
            result[i] = predicates.get(i);
        }
        return result;
    }

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }

}
