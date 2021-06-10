package com.cc.web.entity.projection;

import java.util.List;

public interface DeckFolderProjection {

    long getId();

    void setId(long id);

    String getName();

    void setName();

    List<DeckProjection> getDecks();

    void setDecks(List<DeckProjection> decks);

    List<DeckFolderProjection> getFolders();

    void setFolders(List<DeckFolderProjection> folders);
}