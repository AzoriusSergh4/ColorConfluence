package com.cc.web.card;

import com.cc.web.entity.CardTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

import java.util.List;

public interface CardTranslationRepository extends JpaRepository<CardTranslation, Long>, JpaSpecificationExecutor<CardTranslation>, JpaSpecificationExecutorWithProjection<CardTranslation> {

    List<CardTranslation> findByCard_Name(String name);

}
