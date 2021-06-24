package com.cc.web.format;

import com.cc.web.entity.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormatService {

    private final FormatRepository formatRepository;

    @Autowired
    public FormatService(FormatRepository formatRepository) {
        this.formatRepository = formatRepository;
    }

    /**
     * Get all formats
     * @return the list of formats
     */
    public List<Format> findAllFormats() {
        return formatRepository.findAll();
    }


}
