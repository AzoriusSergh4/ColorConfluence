package com.cc.web.entity.projection;

import com.cc.security.user.User;
import com.cc.web.entity.Format;

public interface DeckProjection {

    long getId();

    void setId();

    String getName();

    void setName();

    String getColors();

    void setColors();

    User getUser();

    void setUser();

    Format getFormat();

    void setFormat();
}
