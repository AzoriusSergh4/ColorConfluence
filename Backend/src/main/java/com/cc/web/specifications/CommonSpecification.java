package com.cc.web.specifications;

import javax.persistence.criteria.Predicate;
import java.text.MessageFormat;
import java.util.List;

public class CommonSpecification {

    protected CommonSpecification(){}

    protected static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }

    protected static Predicate[] predicateListToArray(List<Predicate> predicates){
        var result = new Predicate[predicates.size()];
        for(var i = 0; i< predicates.size(); i++){
            result[i] = predicates.get(i);
        }
        return result;
    }
}
