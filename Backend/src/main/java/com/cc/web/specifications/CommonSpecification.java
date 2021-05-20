package com.cc.web.specifications;

import javax.persistence.criteria.Predicate;
import java.text.MessageFormat;
import java.util.List;

public class CommonSpecification {

    protected static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }

    protected static Predicate[] predicateListToArray(List<Predicate> predicates){
        Predicate[] result = new Predicate[predicates.size()];
        for(int i = 0; i< predicates.size(); i++){
            result[i] = predicates.get(i);
        }
        return result;
    }
}
