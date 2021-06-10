package com.cc.web.deck;

import com.cc.web.entity.DeckFolder;
import com.cc.web.entity.projection.DeckFolderProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeckFolderRepository extends JpaRepository<DeckFolder, Long> {

    Optional<DeckFolderProjection> findByRootIsTrueAndUser_Id(long id);

    DeckFolder getByRootIsTrueAndUser_Id(long id);
}
