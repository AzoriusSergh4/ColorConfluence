package com.cc.web.set;

import com.cc.web.entity.SetInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetInfoRepository extends JpaRepository<SetInfo, Long> {

    boolean existsByCode(String code);
}
