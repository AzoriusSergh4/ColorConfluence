package com.cc.web.card;

import com.cc.web.entity.CardCC;
import com.cc.web.entity.CardTranslation;
import com.cc.web.entity.projection.CardTranslationProjection;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

import java.util.List;

public interface CardTranslationRepository extends JpaRepository<CardTranslation, Long>, JpaSpecificationExecutor<CardTranslation>, JpaSpecificationExecutorWithProjection<CardTranslation> {

	List<CardTranslation> findByCard_Name(String name);

}
